package github.javaxbx.compress;

import github.javaxbx.extension.SPI;

/**
 * 统一压缩接口
 * @author wangtao .
 * @createTime on 2020/10/3
 */

@SPI
public interface Compress {

    byte[] compress(byte[] bytes);


    byte[] decompress(byte[] bytes);
}
