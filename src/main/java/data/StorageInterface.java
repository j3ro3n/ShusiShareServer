package data;

import java.util.ArrayList;

public interface StorageInterface {
    //Get data from MariaDB sushifileshare database
    ArrayList<StorageInterface> loadData() throws Exception;
}
