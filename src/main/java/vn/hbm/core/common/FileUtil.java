package vn.hbm.core.common;

import java.io.*;
import java.util.Date;

public class FileUtil {
    public static String fileContentFromInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try {
            StringBuilder sb = new StringBuilder();

            for(String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            String everything = sb.toString();
            String var5 = everything;
            return var5;
        } finally {
            br.close();
        }
    }

    public static String fileContentFromClasspath(String filePath) throws IOException {
        InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(filePath);
        return is != null ? fileContentFromInputStream(is) : null;
    }

    public static String loadContentFromFile(String fileName) throws IOException {
        File f = new File(fileName);
        if (!f.exists()) {
            return null;
        } else {
            FileInputStream is = new FileInputStream(f);
            return fileContentFromInputStream(is);
        }
    }

    public static boolean storeContentToFile(String content, String fileName) {
        boolean result = true;
        RandomAccessFile m_TextFile = null;

        try {
            File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            }

            m_TextFile = new RandomAccessFile(f, "rw");
            m_TextFile.setLength(0L);
            m_TextFile.write(content.getBytes());
            m_TextFile.close();
        } catch (Exception var13) {
            result = false;
        } finally {
            if (m_TextFile != null) {
                try {
                    m_TextFile.close();
                } catch (IOException var12) {
                }
            }

        }

        return result;
    }

    public static String formatFileName(String fileName, String fileFormat) {
        if (fileName != null && fileName.length() != 0 && fileFormat != null && fileFormat.length() != 0) {
            int extensionIndex = fileName.lastIndexOf(46);
            if (extensionIndex < 0) {
                extensionIndex = fileName.length();
            }

            int baseIndex = fileName.lastIndexOf(47);
            if (baseIndex < 0) {
                baseIndex = fileName.lastIndexOf(92);
            }

            if (baseIndex < 0) {
                baseIndex = 0;
            }

            String baseFileName = fileName.substring(baseIndex, extensionIndex);
            String fileExtension = "";
            if (extensionIndex < fileName.length() - 1) {
                fileExtension = fileName.substring(extensionIndex + 1, fileName.length());
            }

            fileFormat = StringUtils.replaceAll(fileFormat, "$FileName", fileName);
            fileFormat = StringUtils.replaceAll(fileFormat, "$BaseFileName", baseFileName);
            fileFormat = StringUtils.replaceAll(fileFormat, "$FileExtension", fileExtension);
            return fileFormat;
        } else {
            return fileName;
        }
    }

    public static String backup(String sourcePath, String backupPath, String sourceFile, String backupFile, String backupStyle) throws Exception {
        return backup(sourcePath, backupPath, sourceFile, backupFile, backupStyle, true);
    }

    public static String backup(String sourcePath, String backupPath, String sourceFile, String backupFile, String backupStyle, boolean replaceIfExist) throws Exception {
        return backup(sourcePath, backupPath, sourceFile, backupFile, backupStyle, "", replaceIfExist);
    }

    public static String backup(String sourcePath, String backupPath, String sourceFile, String backupFile, String backupStyle, String additionPath) throws Exception {
        return backup(sourcePath, backupPath, sourceFile, backupFile, backupStyle, additionPath, true);
    }

    public static String backup(String sourcePath, String backupPath, String sourceFile, String backupFile, String backupStyle, String additionPath, boolean replaceIfExist) throws Exception {
        if (backupStyle.equals("Delete file")) {
            if (!deleteFile(sourcePath + sourceFile)) {
                throw new Exception("Cannot delete file " + sourcePath + sourceFile);
            }
        } else if (backupPath.length() > 0) {
            String currentDate = "";
            if (backupStyle.equals("Daily")) {
                currentDate = StringUtils.format(new Date(), "yyyyMMdd") + "/";
            } else if (backupStyle.equals("Monthly")) {
                currentDate = StringUtils.format(new Date(), "yyyyMM") + "/";
            } else if (backupStyle.equals("Yearly")) {
                currentDate = StringUtils.format(new Date(), "yyyy") + "/";
            }

            forceFolderExist(backupPath + currentDate + additionPath);
            if (!renameFile(sourcePath + sourceFile, backupPath + currentDate + additionPath + backupFile, replaceIfExist)) {
                throw new Exception("Cannot rename file " + sourcePath + sourceFile + " to " + backupPath + currentDate + backupFile);
            }

            return backupPath + currentDate + backupFile;
        }

        return "";
    }

    public static boolean deleteFile(String source) {
        File sourceFile = new File(source);
        return sourceFile.delete();
    }

    public static boolean renameFile(String source, String destination) {
        return renameFile(source, destination, false);
    }

    public static boolean renameFile(String source, String destination, boolean deleteIfExist) {
        File sourceFile = new File(source);
        File destinationFile = new File(destination);
        return renameFile(sourceFile, destinationFile, deleteIfExist);
    }

    public static boolean renameFile(File sourceFile, File destinationFile) {
        return renameFile(sourceFile, destinationFile, false);
    }

    public static boolean renameFile(File sourceFile, File destinationFile, boolean deleteIfExist) {
        if (sourceFile.getAbsolutePath().equals(destinationFile.getAbsolutePath())) {
            return false;
        } else {
            if (destinationFile.exists()) {
                if (!deleteIfExist) {
                    return false;
                }

                if (!destinationFile.delete()) {
                    return false;
                }
            }

            return sourceFile.renameTo(destinationFile);
        }
    }

    public static void forceFolderExist(String folderPath) throws IOException {
        forceFolderExist(new File(folderPath));
    }

    public static void forceFolderExist(File folder) throws IOException {
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                throw new IOException("Could not create folder " + folder.getPath());
            }
        } else if (!folder.isDirectory()) {
            throw new IOException("A file with name " + folder.getPath() + " already exist");
        }

    }
}
