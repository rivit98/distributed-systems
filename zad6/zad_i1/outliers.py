import os
import numpy as np
import statistics

OUTDIR2 = "./output_processed"

def reject_outliers(data, m=2):
    return data[abs(data - np.mean(data)) < m * np.std(data)]

def save_new_data(path, data):
    tokens = path.split(os.sep)
    parts = tokens[1:]
    new_path = os.path.join(OUTDIR2, *parts)
    folder_path = os.sep.join(new_path.split(os.sep)[:-1])
    # print(new_path, folder_path)
    os.makedirs(folder_path, exist_ok=True)
    with open(new_path, "wt") as f:
        f.write(','.join(map(str, data)))

def main():
    means = {}

    for root, dirs, files in os.walk("./output"):
        for file in files:
            fullpath = os.path.join(root, file)
            # print(fullpath)
            with open(fullpath, "rt") as f:
                data = f.read().split(',')
                data = list(map(int, data))
                a = len(data)
                data = reject_outliers(np.array(data))
                data = data.tolist()
                # print(a, len(data))

                save_new_data(fullpath, data)
                mean = round(statistics.mean(data) / 1000000, 2)
                desc = os.sep.join(fullpath.split(os.sep)[1:])[:-4]
                print(desc.rjust(40, ' '), mean)




if __name__ == '__main__':
    main()