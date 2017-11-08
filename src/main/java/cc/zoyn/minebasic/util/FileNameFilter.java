package cc.zoyn.minebasic.util;

import java.io.File;
import java.io.FileFilter;

/**
 * FileNameFilter<br />
 * it can filter files name
 * 可以过滤文件后缀名
 *
 * @author Zoyn
 * @since 2017-11-05
 */
public class FileNameFilter implements FileFilter {

    private String word;

    public FileNameFilter(String word) {
        this.word = word;
    }

    @Override
    public boolean accept(File file) {
        return file.getName().endsWith(word);
    }
}
