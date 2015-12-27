package appcoo.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import appcoo.util.string.StringUtil;

public abstract class FileUtil {

    // http://www.fileinfo.com/filetypes/video
    public static final String[] VIDEO_EXTENSIONS = {"264", "3g2", "3gp",
            "3gp2", "3gpp", "3gpp2", "3mm", "3p2", "60d", "aep", "ajp", "amv",
            "amx", "arf", "asf", "asx", "avb", "avd", "avi", "avs", "avs",
            "axm", "bdm", "bdmv", "bik", "bin", "bix", "bmk", "box", "bs4",
            "bsf", "byu", "camre", "clpi", "cpi", "cvc", "d2v", "d3v", "dat",
            "dav", "dce", "dck", "ddat", "dif", "dir", "divx", "dlx", "dmb",
            "dmsm", "dmss", "dnc", "dpg", "dream", "dsy", "dv", "dv-avi",
            "dv4", "dvdmedia", "dvr-ms", "dvx", "dxr", "dzm", "dzp", "dzt",
            "evo", "eye", "f4p", "f4v", "fbr", "fbr", "fbz", "fcp", "flc",
            "flh", "fli", "flv", "flx", "gl", "grasp", "gts", "gvi", "gvp",
            "hdmov", "hkm", "ifo", "imovi", "imovi", "iva", "ivf", "ivr",
            "ivs", "izz", "izzy", "jts", "lsf", "lsx", "m15", "m1pg", "m1v",
            "m21", "m21", "m2a", "m2p", "m2t", "m2ts", "m2v", "m4e", "m4u",
            "m4v", "m75", "meta", "mgv", "mj2", "mjp", "mjpg", "mkv", "mmv",
            "mnv", "mod", "modd", "moff", "moi", "moov", "mov", "movie",
            "mp21", "mp21", "mp2v", "mp4", "mp4v", "mpe", "mpeg", "mpeg4",
            "mpf", "mpg", "mpg2", "mpgin", "mpl", "mpls", "mpv", "mpv2", "mqv",
            "msdvd", "msh", "mswmm", "mts", "mtv", "mvb", "mvc", "mvd", "mve",
            "mvp", "mxf", "mys", "ncor", "nsv", "nvc", "ogm", "ogv", "ogx",
            "osp", "par", "pds", "pgi", "piv", "playlist", "pmf", "prel",
            "pro", "prproj", "psh", "pva", "pvr", "pxv", "qt", "qtch", "qtl",
            "qtm", "qtz", "rcproject", "rdb", "rec", "rm", "rmd", "rmp", "rms",
            "rmvb", "roq", "rp", "rts", "rts", "rum", "rv", "sbk", "sbt",
            "scm", "scm", "scn", "sec", "seq", "sfvidcap", "smil", "smk",
            "sml", "smv", "spl", "ssm", "str", "stx", "svi", "swf", "swi",
            "swt", "tda3mt", "tivo", "tix", "tod", "tp", "tp0", "tpd", "tpr",
            "trp", "ts", "tvs", "vc1", "vcr", "vcv", "vdo", "vdr", "veg",
            "vem", "vf", "vfw", "vfz", "vgz", "vid", "viewlet", "viv", "vivo",
            "vlab", "vob", "vp3", "vp6", "vp7", "vpj", "vro", "vsp", "w32",
            "wcp", "webm", "wm", "wmd", "wmmp", "wmv", "wmx", "wp3", "wpl",
            "wtv", "wvx", "xfl", "xvid", "yuv", "zm1", "zm2", "zm3", "zmv"};
    // http://www.fileinfo.com/filetypes/audio
    public static final String[] AUDIO_EXTENSIONS = {"4mp", "669", "6cm",
            "8cm", "8med", "8svx", "a2m", "aa", "aa3", "aac", "aax", "abc",
            "abm", "ac3", "acd", "acd-bak", "acd-zip", "acm", "act", "adg",
            "afc", "agm", "ahx", "aif", "aifc", "aiff", "ais", "akp", "al",
            "alaw", "all", "amf", "amr", "ams", "ams", "aob", "ape", "apf",
            "apl", "ase", "at3", "atrac", "au", "aud", "aup", "avr", "awb",
            "band", "bap", "bdd", "box", "bun", "bwf", "c01", "caf", "cda",
            "cdda", "cdr", "cel", "cfa", "cidb", "cmf", "copy", "cpr", "cpt",
            "csh", "cwp", "d00", "d01", "dcf", "dcm", "dct", "ddt", "dewf",
            "df2", "dfc", "dig", "dig", "dls", "dm", "dmf", "dmsa", "dmse",
            "drg", "dsf", "dsm", "dsp", "dss", "dtm", "dts", "dtshd", "dvf",
            "dwd", "ear", "efa", "efe", "efk", "efq", "efs", "efv", "emd",
            "emp", "emx", "esps", "f2r", "f32", "f3r", "f4a", "f64", "far",
            "fff", "flac", "flp", "fls", "frg", "fsm", "fzb", "fzf", "fzv",
            "g721", "g723", "g726", "gig", "gp5", "gpk", "gsm", "gsm", "h0",
            "hdp", "hma", "hsb", "ics", "iff", "imf", "imp", "ins", "ins",
            "it", "iti", "its", "jam", "k25", "k26", "kar", "kin", "kit",
            "kmp", "koz", "koz", "kpl", "krz", "ksc", "ksf", "kt2", "kt3",
            "ktp", "l", "la", "lqt", "lso", "lvp", "lwv", "m1a", "m3u", "m4a",
            "m4b", "m4p", "m4r", "ma1", "mdl", "med", "mgv", "mid", "midi",
            "miniusf", "mka", "mlp", "mmf", "mmm", "mmp", "mo3", "mod", "mp1",
            "mp2", "mp3", "mpa", "mpc", "mpga", "mpu", "mp_", "mscx", "mscz",
            "msv", "mt2", "mt9", "mte", "mti", "mtm", "mtp", "mts", "mus",
            "mws", "mxl", "mzp", "nap", "nki", "nra", "nrt", "nsa", "nsf",
            "nst", "ntn", "nvf", "nwc", "odm", "oga", "ogg", "okt", "oma",
            "omf", "omg", "omx", "ots", "ove", "ovw", "pac", "pat", "pbf",
            "pca", "pcast", "pcg", "pcm", "peak", "phy", "pk", "pla", "pls",
            "pna", "ppc", "ppcx", "prg", "prg", "psf", "psm", "ptf", "ptm",
            "pts", "pvc", "qcp", "r", "r1m", "ra", "ram", "raw", "rax", "rbs",
            "rcy", "rex", "rfl", "rmf", "rmi", "rmj", "rmm", "rmx", "rng",
            "rns", "rol", "rsn", "rso", "rti", "rtm", "rts", "rvx", "rx2",
            "s3i", "s3m", "s3z", "saf", "sam", "sb", "sbg", "sbi", "sbk",
            "sc2", "sd", "sd", "sd2", "sd2f", "sdat", "sdii", "sds", "sdt",
            "sdx", "seg", "seq", "ses", "sf", "sf2", "sfk", "sfl", "shn",
            "sib", "sid", "sid", "smf", "smp", "snd", "snd", "snd", "sng",
            "sng", "sou", "sppack", "sprg", "spx", "sseq", "sseq", "ssnd",
            "stm", "stx", "sty", "svx", "sw", "swa", "syh", "syw", "syx",
            "td0", "tfmx", "thx", "toc", "tsp", "txw", "u", "ub", "ulaw",
            "ult", "ulw", "uni", "usf", "usflib", "uw", "uwf", "vag", "val",
            "vc3", "vmd", "vmf", "vmf", "voc", "voi", "vox", "vpm", "vqf",
            "vrf", "vyf", "w01", "wav", "wav", "wave", "wax", "wfb", "wfd",
            "wfp", "wma", "wow", "wpk", "wproj", "wrk", "wus", "wut", "wv",
            "wvc", "wve", "wwu", "xa", "xa", "xfs", "xi", "xm", "xmf", "xmi",
            "xmz", "xp", "xrns", "xsb", "xspf", "xt", "xwb", "ym", "zvd", "zvr"};
    private static final HashSet<String> mHashVideo;
    private static final HashSet<String> mHashAudio;

    static {
        mHashVideo = new HashSet<String>(Arrays.asList(VIDEO_EXTENSIONS));
        mHashAudio = new HashSet<String>(Arrays.asList(AUDIO_EXTENSIONS));
    }

    private FileUtil() {

    }

    /**
     * 是否是音频或者视频
     */
    public static boolean isVideoOrAudio(File f) {
        final String ext = getFileExtension(f);
        return mHashVideo.contains(ext) || mHashAudio.contains(ext);
    }

    /**
     * copy file
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sourceFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        }
        return writeFile(destFilePath, inputStream);
    }

    /**
     * read file to string list, a element of list is a line
     *
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static List<String> readFileToList(String filePath, String charsetName) {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<String>();
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * read file
     *
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file
     *
     * @param filePath
     * @param content
     * @param append   is append, if true, write to the end of file, else clear content of file and write into it
     * @return return false if content is empty, true otherwise
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, String content, boolean append) {
        if (StringUtil.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file
     *
     * @param filePath
     * @param contentList
     * @param append      is append, if true, write to the end of file, else clear content of file and write into it
     * @return return false if contentList is empty, true otherwise
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
        if (contentList == null || contentList.size() == 0) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            int i = 0;
            for (String line : contentList) {
                if (i++ > 0) {
                    fileWriter.write("\r\n");
                }
                fileWriter.write(line);
            }
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file
     *
     * @param file   the file to be opened for writing.
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(File file, InputStream stream, boolean append) {
        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (o != null) {
                try {
                    o.close();
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file, the string will be written to the begin of the file
     *
     * @param filePath
     * @param content
     * @return
     */
    public static boolean writeFile(String filePath, String content) {
        return writeFile(filePath, content, false);
    }

    /**
     * write file, the string list will be written to the begin of the file
     *
     * @param filePath
     * @param contentList
     * @return
     */
    public static boolean writeFile(String filePath, List<String> contentList) {
        return writeFile(filePath, contentList, false);
    }

    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param filePath
     * @param stream
     * @return
     * @see {@link #writeFile(String, InputStream, boolean)}
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        return writeFile(filePath, stream, false);
    }

    /**
     * write file
     *
     * @param file   the file to be opened for writing.
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(String filePath, InputStream stream, boolean append) {
        return writeFile(filePath != null ? new File(filePath) : null, stream, append);
    }

    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param file
     * @param stream
     * @return
     * @see {@link #writeFile(File, InputStream, boolean)}
     */
    public static boolean writeFile(File file, InputStream stream) {
        return writeFile(file, stream, false);
    }

    /**
     * Creates the directory named by the trailing filename of this file, including the complete directory path required
     * to create this directory. <br/>
     * <br/>
     * <ul>
     * <strong>Attentions:</strong>
     * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
     * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
     * </ul>
     *
     * @param filePath
     * @return true if the necessary directories have been created or the target directory already exists, false one of
     * the directories can not be created.
     * <ul>
     * <li>if {@link FileUtils#getFolderName(String)} return null, return false</li>
     * <li>if target directory already exists, return true</li>
     * <li>return {@link java.io.File#makeFolder}</li>
     * </ul>
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (StringUtil.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    /**
     * get folder name from path
     * <p/>
     * <pre>
     *      getFolderName(null)               =   null
     *      getFolderName("")                 =   ""
     *      getFolderName("   ")              =   ""
     *      getFolderName("a.mp3")            =   ""
     *      getFolderName("a.b.rmvb")         =   ""
     *      getFolderName("abc")              =   ""
     *      getFolderName("c:\\")              =   "c:"
     *      getFolderName("c:\\a")             =   "c:"
     *      getFolderName("c:\\a.b")           =   "c:"
     *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
     *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
     *      getFolderName("/home/admin")      =   "/home"
     *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {

        if (StringUtil.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * @param filePath
     * @return
     * @see #makeDirs(String)
     */
    public static boolean makeFolders(String filePath) {
        return makeDirs(filePath);
    }

    /**
     * Indicates if this file represents a file on the underlying file system.
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if (StringUtil.isBlank(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * Indicates if this file represents a directory on the underlying file system.
     *
     * @param directoryPath
     * @return
     */
    public static boolean isFolderExist(String directoryPath) {
        if (StringUtil.isBlank(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * 获取文件后缀
     */
    public static String getFileExtension(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }

    public static String getFileNameNoExtension(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 获取文件大小 **
     */
    public static long getFileSize(File f) throws Exception {
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
        } else {
            f.createNewFile();
        }
        return s;
    }

    /**
     * 获取文件夹大小 **
     */
    public static long getDirectorySize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 转换文件大小单位(b/kb/mb/gb) **
     */
    public static String formatFileSize(long size) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取文件个数 **
     */
    public static long getDirectoryFileCount(File f) {// 递归求取目录文件个数
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getDirectoryFileCount(flist[i]);
                size--;
            }
        }
        return size;
    }

    /**
     * delete file or directory
     * <ul>
     * <li>if path is null or empty, return true</li>
     * <li>if path not exist, return true</li>
     * <li>if path exist, delete recursion. return true</li>
     * <ul>
     */
    public static boolean deleteFile(String path) {
        if (StringUtil.isBlank(path)) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }


    public static boolean hasSDCard(){

        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }
}
