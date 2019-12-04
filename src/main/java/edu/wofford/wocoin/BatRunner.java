package edu.wofford.wocoin;

import java.io.File;
import java.io.OutputStreamWriter;

import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;

/**
 * @author pavan.solapure
 *
 */
public class BatRunner {

    public BatRunner() {
        String batfile = "setupBatNode.bat";
        String directory = "ethereum/setupBatNode.bat";
        try {
            runProcess(batfile, directory);
        } catch (CommandLineException e) {
            e.printStackTrace();
        }
    }


    public void runProcess(String batfile, String directory) throws CommandLineException {

        Commandline commandLine = new Commandline();

        File executable = new File(directory + "/" +batfile);
        commandLine.setExecutable(executable.getAbsolutePath());

        WriterStreamConsumer systemOut = new WriterStreamConsumer(
                new OutputStreamWriter(System.out));

        WriterStreamConsumer systemErr = new WriterStreamConsumer(
                new OutputStreamWriter(System.out));

        int returnCode = CommandLineUtils.executeCommandLine(commandLine, systemOut, systemErr);
        if (returnCode != 0) {
            System.out.println("Something Bad Happened!");
        } else {
            System.out.println("Taaa!! ddaaaaa!!");
        }
    }




}