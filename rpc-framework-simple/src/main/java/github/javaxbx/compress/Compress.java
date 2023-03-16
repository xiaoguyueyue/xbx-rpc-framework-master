package github.javaxbx.compress;

import github.javaxbx.extension.SPI;

/**
 * 统一压缩接口
 * @author xiaobiaoxu
 * @createTime on 2022/10/3
 */

@SPI
public interface Compress {

    byte[] compress(byte[] bytes);


    byte[] decompress(byte[] bytes);
}
