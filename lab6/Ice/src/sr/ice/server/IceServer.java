package sr.ice.server;
// **********************************************************************
//
// Copyright (c) 2003-2019 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Identity;

import java.util.Arrays;

public class IceServer
{
	public void t1(String[] args)
	{
		int status = 0;
		Communicator communicator = null;

		try	{
			// 1. Inicjalizacja ICE - utworzenie communicatora
			communicator = Util.initialize(args);

			// 2. Konfiguracja adaptera
			// METODA 1 (polecana produkcyjnie): Konfiguracja adaptera Adapter1 jest w pliku konfiguracyjnym podanym jako parametr uruchomienia serwera
			ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");  
			
			// METODA 2 (niepolecana, dopuszczalna testowo): Konfiguracja adaptera Adapter1 jest w kodzie �r�d�owym
			//ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h 127.0.0.2 -p 10000");
			//ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h 127.0.0.2 -p 10000 : udp -h 127.0.0.2 -p 10000");
			//ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter2", "tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000 -z");

			// 3. Stworzenie serwanta/serwant�w
			CalcI calcServant1 = new CalcI();    
			CalcI calcServant2 = new CalcI();    			
			CalcI calcServant3 = new CalcI();

			// 4. Dodanie wpis�w do tablicy ASM, skojarzenie nazwy obiektu (Identity) z serwantem 
			adapter.add(calcServant1, new Identity("calc11", "calc"));
	        adapter.add(calcServant2, new Identity("calc22", "calc"));
	        adapter.add(calcServant3, new Identity("calc33", "calc"));

			// 5. Aktywacja adaptera i wej�cie w p�tl� przetwarzania ��da�
			adapter.activate();
			
			System.out.println("Entering event processing loop...");
			
			communicator.waitForShutdown(); 		
			
		}
		catch (Exception e) {
			System.err.println(e);
			status = 1;
		}
		if (communicator != null) {
			try {
				communicator.destroy();
			}
			catch (Exception e) {
				System.err.println(e);
				status = 1;
			}
		}
		System.exit(status);
	}

	public static void main(String[] args)
	{
		IceServer app = new IceServer();
		app.t1(args);
	}
}
