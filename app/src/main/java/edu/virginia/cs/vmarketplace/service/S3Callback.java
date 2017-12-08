package edu.virginia.cs.vmarketplace.service;

import java.io.File;
import java.util.List;

/**
 * Created by cutehuazai on 12/7/17.
 */
@FunctionalInterface
public interface S3Callback {
    public void runCallback(List<File> fileList);
}
