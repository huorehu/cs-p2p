package com.shpp.p2p.cs.sushakov.assignment14.compressor;

/** Analyzer of command line interpreter */
public class AnalyzerCLI {

    /** Form of archive file name */
    private static final String ARCHIVE_NAME_REGEXP = "(\\w+.)+par";
    /** Archive file extension */
    private static final String ARCHIVE_EXTENSION = ".par";

    /** Source file */
    private String srcFile;
    /** Destination file */
    private String dstFile;
    /** Archiving or unarchiving process */
    private boolean compressing;
    /** Arguments of CLI is correct */
    private boolean isCorrectArgs;

    public AnalyzerCLI(String[] args) {
        alalyzeCLI(args);
    }

    /** @return source file name */
    public String getSrcFile() {
        return srcFile;
    }

    /** @return destination file name */
    public String getDstFile() {
        return dstFile;
    }

    /** @return archiving or unarchiving process name */
    public boolean isCompression() {
        return compressing;
    }

    /** @return correctness of the entered arguments */
    public boolean isCorrectArgs() {
        return isCorrectArgs;
    }

    /**
     * Analyze command line and specifies the names of the source file and the destination file.
     * Also specifies whether to archive or unarchive.
     *
     * @param args the arguments for analyzing.
     */
    private void alalyzeCLI(String[] args) {
        int amountArgs = args.length;

        switch (amountArgs) {
            case 0:
                srcFile = "simple.txt";
                dstFile = srcFile + ARCHIVE_EXTENSION;
                compressing = true;
                isCorrectArgs = true;
                break;
            case 1:
                srcFile = args[0];

                if (srcFile.matches(ARCHIVE_NAME_REGEXP)) {
                    compressing = false;
                    dstFile = srcFile.substring(0, srcFile.length() - ARCHIVE_EXTENSION.length());
                } else {
                    compressing = true;
                    dstFile = srcFile + ARCHIVE_EXTENSION;
                }

                isCorrectArgs = true;
                break;
            case 2:
                srcFile = args[0];
                dstFile = args[1];
                compressing = !srcFile.matches(ARCHIVE_NAME_REGEXP);
                isCorrectArgs = true;
                break;
            case 3:
                String key = args[0];
                srcFile = args[1];
                dstFile = args[2];

                if ("-a".equals(key)) {
                    compressing = true;
                    isCorrectArgs = true;
                } else if ("-u".equals(key)) {
                    compressing = false;
                    isCorrectArgs = true;
                } else {
                    isCorrectArgs = false;
                }
                break;
            default:
                isCorrectArgs = false;
        }
    }

}
