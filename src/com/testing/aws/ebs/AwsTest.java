package com.testing.aws.ebs;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AwsTest {

  final File folder = new File("/mnt/test/");
  File[] listofFiles = folder.listFiles();

    public void run(int cores) {
        long startTime = System.nanoTime();
        System.out.println("AWS EBS test started at: " + startTime);
        runTest(cores);
        long finishTime = System.nanoTime();
        System.out.println("AWS EBS test finished at: " + finishTime);

        long duration = (finishTime - startTime);
        System.out.println("Total time is " + duration/1000000 + " milliseconds");
        System.out.println("Total time is approx " + duration/1000000000 + " seconds");

    }

    // take a fileNumber then generate that many 1GB files
    private void setupTest(int fileNumber) {
      //TODO: Currently this is done manually with the following command:
      // dd if=/dev/urandom of=file1.txt bs=10737418 count=100
      // for example to create 10 files:
      // for i in {0001..0010} ; do dd if=/dev/urandom of=file$i.txt bs=10737418 count=100 ; done
    }

    private void readFile(File file) {
      try {
        // Use this for reading the data.
        byte[] buffer = new byte[2048];

        FileInputStream inputStream = new FileInputStream(file.toString());

        // read fills buffer with data and returns
        // the number of bytes read (which of course
        // may be less than the buffer size, but
        // it will never be more).
        int total = 0;
        int nRead = 0;
        while((nRead = inputStream.read(buffer)) != -1) {
          total += nRead;
        }

        // Always close files.
        inputStream.close();

        System.out.println("Read " + total + " bytes");
      }
      catch(FileNotFoundException ex) {
        System.out.println(
            "Unable to open file '" +
            file.toString() + "'");
      }
      catch(IOException ex) {
        System.out.println(
            "Error reading file '"
            + file.toString() + "'");

      }
    }

    private void runTest(int cores) {
        try {
          for (File file : listofFiles ) {
            System.out.println("Reading file: " + file.toString());
            readFile(file);
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
      if (args.length != 1) {
        System.out.println("Usage: ./AwsTest {CPU cores}");
        System.out.println("Please make sure to run this command as well to generate random files:");
        System.out.println("for i in {0001..0010} ; do dd if=/dev/urandom of=file$i.txt bs=10737418 count=100 ; done");
        System.exit(1);
      } else {
        System.out.println("Running with args[0] cores");
      }


      AwsTest test = new AwsTest();
      test.run(Integer.parseInt(args[0]));

    }
}
