/*
 * $HeadURL:  $
 *
 * Copyright (c) 2007 Drutt Corporation, all rights reserved.
 *
 */
package org.genjms.client.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

/**
 * @author elihuwu
 * @version $Revision: $
 */
public class CmdProcessor {

    /** Class revision */
    public static final String _REV_ID_ = "$Revision: $";
    
    protected String cmdLineSyntax = "queue-consume.sh [OPTION] [TEXT]\n";;
    
    private final static String DESTINATION_OPT = "d";
    
    private final static String DESTINATION_OPT_LONG = "destination";
    
    private final static String ADDRESS_OPT= "a";
    
    private final static String ADDRESS_OPT_LONG = "address";
    
    private final static String CLIENT_NUM_OPT= "c";
    
    private final static String CLIENT_NUM_OPT_LONG = "client-number";
    
    private final static String CLIENT_NAME_OPT= "n";
    
    private final static String CLIENT_NAME_OPT_LONG = "client-name";
    
    private String destination = "Consumer.jms.VirtualTopic.TopicB";
	private String jmsUri = "tcp://ecnshxenlx0215:61616";
	private int clientNumber = 3;
	private String clientName = null;
    
    
    public int process(String[] args){
        Options options = getOptions();
        
        Option optionH = new Option("h","help", true, "show usage information");
        optionH.setOptionalArg(true);
        optionH.setArgName(null);
        options.addOption(optionH);
        
        CommandLineParser cliParser = new DefaultParser();
        try {
            CommandLine line =  cliParser.parse(options, args);
            
            if(line.getOptions().length == 0 || line.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.setWidth(200);
                formatter.printHelp(cmdLineSyntax, "available options", options, "");
                return 1;
            }
            
            return processOptions(line);
            
         } catch (UnrecognizedOptionException e){
             String invalidOption = e.getOption();
             String firstLine = String.format("Invalid option -- '%s'", args[0], invalidOption);
             String secondLine = "Try '-h' for more information.";
             System.out.println(firstLine);
             System.out.println(secondLine);
         } catch (ParseException e) {
             throw new IllegalStateException(e);
         }
        return 0;
    }

    /**
     * @param options
     */
    protected int processOptions(CommandLine cmdLine) {
    	if (cmdLine.hasOption(DESTINATION_OPT)) {
    		this.destination = cmdLine.getOptionValue(DESTINATION_OPT);
    		if(destination == null) {
    			return 1;
    		}
        }
        
        if (cmdLine.hasOption(ADDRESS_OPT)) {
        	this.jmsUri = cmdLine.getOptionValue(ADDRESS_OPT);
        	if(jmsUri == null) {
    			return 1;
    		}
        }
        
        if (cmdLine.hasOption(CLIENT_NUM_OPT)) {
        	String value = cmdLine.getOptionValue(CLIENT_NUM_OPT);
    		this.clientNumber = value == null? 1 : Integer.parseInt(value);
        }
        
        if (cmdLine.hasOption(CLIENT_NAME_OPT)) {
        	this.clientName = cmdLine.getOptionValue(CLIENT_NAME_OPT);
        }
        
        return 0;
    }

    /**
     * @return
     */
    protected Options getOptions(){
    	Options options = new Options();
        Option optionDestination = new Option(DESTINATION_OPT,DESTINATION_OPT_LONG, true, "queue destination, such as 'Consumer.jms.VirtualTopic.TopicB'");
        optionDestination.setArgName("queue destination");
        
        Option optionAddress = new Option(ADDRESS_OPT,ADDRESS_OPT_LONG, true, "jms address, such as tcp://ecnshxenlx0215:61616");
        optionAddress.setArgName("jms address");
        
        Option optionClientNum = new Option(CLIENT_NUM_OPT,CLIENT_NUM_OPT_LONG, true, "client number, such as 3. Default value is 1");
        optionClientNum.setArgName("client number");
        
        Option optionClientName = new Option(CLIENT_NAME_OPT,CLIENT_NAME_OPT_LONG, true, "client name, such as name1. Defaul value is ip address");
        optionClientName.setArgName("client name");
        
        options.addOption(optionAddress);
        options.addOption(optionDestination);
        options.addOption(optionClientNum);
        options.addOption(optionClientName);
        
        return options;
    }
    
    public String getDestination() {
		return destination;
	}

	public String getJmsUri() {
		return jmsUri;
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public String getClientName() {
		return clientName;
	}
    
    

}
