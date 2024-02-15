package pl.npesystem.services;

import com.helger.commons.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import org.springframework.stereotype.*;
import pl.npesystem.data.*;

import java.io.*;
import java.time.*;
import java.util.*;

@Service
@Singleton
public class LogService {

    List<LogInfo> logInfos = new ArrayList<>();

    public LogService() {

    }

    public void addFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                logInfos.add(new LogInfo(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Page<LogInfo> list(Pageable pageable) {
        Page<LogInfo> page = new PageImpl<>(logInfos, pageable, logInfos.size());

//        return repository.findAll(pageable);
        return page;
    }

    public Page<LogInfo> list(Pageable pageable, Specification<LogInfo> filter) {
//        return repository.findAll(filter, pageable);
        return new PageImpl<>(logInfos, pageable, logInfos.size());
    }

    public int count() {
        return (int) logInfos.size();
    }

}

