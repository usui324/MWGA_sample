package main.entry;

import main.evaluate.EaMySns;
import main.network.NmConnectingNearestNeighbor;
import output.file.FileTransfer;
import output.file.ParameterFile;
import output.file.SetDirectory;

public class Launcher extends Thread implements Parameter{

	public static void main(String[] args) {

		//-----------------------------------------------------------

		EaMetaRewardGame ea = new EaMetaRewardGame();

		//-----------------------------------------------------------

		NmCompleteGragh nm = new NmCompleteGragh();

		//-----------------------------------------------------------

		//time stanmp1
		long startTime = System.currentTimeMillis();

		//set directory
		SetDirectory sd = new SetDirectory();
		String pathName = sd.set();

		//file transfer
		FileTransfer ft = new FileTransfer(pathName);

		//make file " parameters "
		ParameterFile pf = new ParameterFile(nm, ea);
		ft.transfer(pf.write());

		//run simulation
		SubLauncher sl = new SubLauncher(nm, ea, ft, sd);
		sl.start();
		//SubLauncher sl2 = new SubLauncher(nm, ea, ft);
		//sl2.start();


		try {

			sl.join();
			//sl2.join();

		}catch(InterruptedException e) {
			System.out.println(e);
		}


		//time stamp2
		long finishTime = System.currentTimeMillis();
		System.out.println(" Time : " + (finishTime - startTime));

		System.out.println("Thats All.");
	}






}
