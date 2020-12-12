package mk.ukim.finki.aud6.benford;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface NumbersReader {
    List<Integer> read(InputStream inputStream);
}
