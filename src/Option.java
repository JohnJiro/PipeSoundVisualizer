import java.io.*;

public class Option {
    public static void set(Main main, String[] args) {
        int i = 0;

        try {
            while (i < args.length) {
                switch (args[i]) {
                    case "-r":
                        main.freq = Integer.parseInt(args[++i]);

                        break;
                    case "-b":
                        main.bit = Integer.parseInt(args[++i]);

                        break;
                    case "-c":
                        main.ch = Integer.parseInt(args[++i]);

                        break;
                    case "-l":
                        main.bufferedSampleLength = Integer.parseInt(args[++i]);

                        break;
                    case "-s":
                        main.spectrogramLength = Integer.parseInt(args[++i]);

                        break;
                    case "-fw":
                        main.frameWidth = Integer.parseInt(args[++i]);

                        break;
                    case "-fh":
                        main.frameHeight = Integer.parseInt(args[++i]);

                        break;
                    case "-fx":
                        main.frameX = Integer.parseInt(args[++i]);

                        break;
                    case "-fy":
                        main.frameY = Integer.parseInt(args[++i]);

                        break;
                    case "--col":
                        main.frameColumn = Integer.parseInt(args[++i]);

                        break;
                    case "--single":
                        main.useMainFrame = true;

                        break;
                    case "-v":
                    case "--version":
                        System.err.println(main.TITLE + " " + main.VERSION);

                        System.exit(0);

                        return;
                    case "-h":
                    case "--help":
                        printTextFile("help.txt");

                        System.exit(0);

                        return;
                    default:
                        System.err.println(String.format("オプション「%s」は存在しません。", args[i]));

                        System.exit(1);

                        return;
                }

                i++;
            }
        } catch (NumberFormatException _) {
            System.err.println(String.format("%d番目の引数には数値を指定する必要があります。", i));

            System.exit(1);
        } catch (ArrayIndexOutOfBoundsException _) {
            System.err.println("オプションの後ろに値を指定する必要があります。");

            System.exit(1);
        }
    }

    private static void printTextFile(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));

            String line;

            while ((line = bufferedReader.readLine()) != null) System.err.println(line);

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
