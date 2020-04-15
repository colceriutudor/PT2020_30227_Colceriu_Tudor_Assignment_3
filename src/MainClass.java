import FileProcessor.*;
import ProcessCommands.*;

public class MainClass {
        static String command;
    public static void main(String[] args) throws Exception {
        ProcessFile commands = new ProcessFile(args[0]);
        commands.readCommandFile(); //initializeaza fisierul din care se citesc comenzi
        CommandProcessor commander = new CommandProcessor();
        while((command = commands.readLine()) != null){ //atata timp cat mai sunt comenzi, le citim
            commander.processCommand(command);
        }
    }
}
