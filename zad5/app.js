require('dotenv').config();
const axios = require('axios');
const express = require('express');
const app = express();
const port = 3000;

app.set('views', './views');
app.set('view engine', 'pug');

axios.interceptors.request.use((config) => {
    config.url = encodeURI(config.url);
    return config;
});

const getOnePage = async (title, plot, type, pageNr) => {
    let query = `http://www.omdbapi.com/?s=${title}&page=${pageNr}&apikey=${process.env.API_KEY}`;
    if (type !== 'all') {
        query += `&type=${type}`;
    }

    const response = await axios.get(query);
    return response.data;
};

const getRatings = async (video) => {
    const response = await axios.get(
        `https://imdb-api.com/en/API/Ratings/${process.env.API_KEY2}/${video.imdbID}`
    );
    const data = response.data;
    if (data.errorMessage.length !== 0) {
        video.rating = 0;
        return video;
    }

    const ratings = [
        data.imDb * 1.0,
        data.metacritic / 10.0,
        data.theMovieDb * 1.0,
        data.rottenTomatoes * 1.0,
        data.tV_com * 1.0,
        data.filmAffinity * 1.0
    ];

    const sum = ratings.reduce((a, b) => a + b, 0);
    const avg = (sum / ratings.length || 0).toFixed(2);
    video.rating = avg;
    return video;
};

const getUserRatings = async (video) => {
    const response = await axios.get(
        `https://imdb-api.com/en/API/UserRatings/${process.env.API_KEY2}/${video.imdbID}`
    );
    const data = response.data;
    if (data.errorMessage.length !== 0) {
        video.userRating = 0;
        return video;
    }

    const totalVotesNum = data.totalRatingVotes;
    const sum = data.ratings
        .map((rating) => rating.rating * rating.votes)
        .reduce((a, b) => a + b, 0);

    const avg = sum / totalVotesNum;
    video.userRating = avg.toFixed(2);
    video.totalVotes = totalVotesNum;
    return video;
};

const getDetails = async (video, plot) => {
    const response = await axios.get(
        `http://www.omdbapi.com/?i=${video.imdbID}&plot=${plot}&apikey=${process.env.API_KEY}`
    );
    const data = response.data;
    if (data.Response !== 'True') {
        return video;
    }

    return { ...video, ...data };
};

const yearFilter = (videos, yrStart, yrEnd) => {
    return videos.filter((vid) => {
        const tokens = vid.Year.split('–').filter((tok) => tok.length > 0);

        if (tokens.length === 1) {
            const yr = parseInt(tokens[0]);
            return yrStart <= yr && yr <= yrEnd;
        } else if (tokens.length === 0) {
            return true;
        } else {
            const [yr1, yr2] = [parseInt(tokens[0]), parseInt(tokens[1])];
            return (yrStart <= yr1 && yr1 <= yrEnd) || (yrStart <= yr2 && yr2 <= yrEnd);
        }
    });
};

app.get('/', (req, res) => {
    res.render('index');
});

app.get('/search', async (req, res) => {
    const title = req.query.title;
    const plot = req.query.plot || 'short';
    const type = req.query.type || 'all';
    const actors = req.query.actors === 'on';
    const yearStart = req.query.yearStart || 0;
    const yearEnd = req.query.yearEnd || 9999;

    try {
        const data = await Promise.all([1].map((pageNr) => getOnePage(title, plot, type, pageNr)));

        const videos = data
            .filter((obj) => obj.Response === 'True')
            .map((obj) => obj.Search)
            .flat();

        const filteredVideos = yearFilter(videos, yearStart, yearEnd);

        const withSiteRanking = await Promise.all(filteredVideos.map((vid) => getRatings(vid)));
        const withUserRatings = await Promise.all(
            withSiteRanking.map((vid) => getUserRatings(vid))
        );
        const withPlots = await Promise.all(withUserRatings.map((vid) => getDetails(vid, plot)));

        res.render('results', { videos: withPlots, displayActors: actors });
    } catch (e) {
        res.render('error', {
            errors: JSON.stringify(e.message)
        });
    }
});

app.listen(port, () => {
    console.log(`Server listening at http://localhost:${port}`);
});
