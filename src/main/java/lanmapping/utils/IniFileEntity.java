package lanmapping.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * int配置信息类
 * @author meanvalue
 * Date: 2021/12/5 21:05
 */
@Data
@AllArgsConstructor
public class IniFileEntity {
    private String section;
    private String key;
    private String value;
    public static void createIniFile(String filePath, List<IniFileEntity> fileContent) throws IOException {
        File file = new File(filePath);
        file.delete();
        file.createNewFile();
        Ini ini = new Ini();
        ini.load(file);
        fileContent.forEach((entity)-> ini.add(entity.getSection(), entity.getKey(), entity.getValue() == null ? "" : entity.getValue()));
        ini.store(file);
    }
}
