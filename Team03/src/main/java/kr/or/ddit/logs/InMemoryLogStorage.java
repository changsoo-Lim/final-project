//package kr.or.ddit.logs;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class InMemoryLogStorage {
//	
//	private static final List<String> logs = Collections.synchronizedList(new ArrayList<>());
//
//    public static void addLog(String log) {
//        logs.add(log);
//    }
//
//    public static List<String> getLogs() {
//        return Collections.unmodifiableList(logs);
//    }
//
//    public static void clearLogs() {
//        logs.clear(); // 로그 초기화 기능
//    }
//}
