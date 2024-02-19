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
                try {
                    logInfos.add(new LogInfo(line));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public Page<LogInfo> list(Pageable pageable) {
        Page<LogInfo> page = new PageImpl<>(logInfos, pageable, logInfos.size());

//        return repository.findAll(pageable);
        return page;
    }

    public Page<LogInfo> list(Pageable pageable, Specification<LogInfo> filter) {
//        return repository.findAll(filter, pageable);
        System.out.println((int) pageable.getOffset() + "   " + pageable.getPageSize());
        Sort sort = pageable.getSort();
        List<LogInfo> logInfos = this.logInfos;
        if (sort.isSorted()) {
            logInfos.sort((o1, o2) -> {
                int result = 0;
                for (Sort.Order order : sort) {
                    switch (order.getProperty()) {
                        case "date":
                            result = o1.getDate().compareTo(o2.getDate());
                            break;
                        case "threadName":
                            result = o1.getThreadName().compareTo(o2.getThreadName());
                            break;
                        case "logLevel":
                            result = o1.getLogLevel().compareTo(o2.getLogLevel());
                            break;
                        case "className":
                            result = o1.getClassName().compareTo(o2.getClassName());
                            break;
                        case "message":
                            result = o1.getMessage().compareTo(o2.getMessage());
                            break;
                    }
                    if (result != 0) {
                        return order.isAscending() ? result : -result;
                    }
                }
				return result;
			});
        }  else {
            logInfos.sort(Comparator.comparing(LogInfo::getDate));
        }

        return new PageImpl<>(logInfos.subList(pageable.getPageNumber() * 50, (pageable.getPageNumber() * 50) + 50  ), pageable, logInfos.size());
    }

    public int count() {
        return (int) logInfos.size();
    }

}

