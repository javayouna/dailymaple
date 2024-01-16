package youna.dailymaple.com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private static final String API_KEY ="test_d026676e1f53d6bb19f68f0e3ed79ce25917d012a74f046a29f8228237db57e4db56d42701724d4c28fa3b3e9823c249";
	
	/* 캐릭터 ocid 조회 */
	@RequestMapping(value = "/ocidApi/{charId}", method = RequestMethod.GET)
	public ResponseEntity<String> ocidApi(@PathVariable String charId) {
		logger.info("넘어오냐=====");
		logger.info("charId :"+charId);
		String ocid = null;
		try {
	            String characterName = URLEncoder.encode(charId, "UTF-8");
	            String urlString = "https://open.api.nexon.com/maplestory/v1/id?character_name=" + charId;
	            System.out.println("urlString: " + urlString);
	            URL url = new URL(urlString);

	        
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");
	            connection.setRequestProperty("x-nxopen-api-key", API_KEY);

	            int responseCode = connection.getResponseCode();

	            BufferedReader in;
	            if (responseCode == 200) {
	            	in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            } else {
	                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
	            }

	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }

	            // StringBuffer를 String으로 변환
	            String responseString = response.toString();

	            // "ocid" 키 문자열 찾기
	            String ocidKey = "\"ocid\":\"";

	            // "ocid" 키의 시작 위치 찾기
	            int startIndex = responseString.indexOf(ocidKey);
	            if (startIndex != -1) {
	                // 시작 위치 이후의 부분 가져오기
	                String ocidSubString = responseString.substring(startIndex + ocidKey.length());

	                // "ocid" 값 추출
	                int endIndex = ocidSubString.indexOf("\"");
	                if (endIndex != -1) {
	                    ocid = ocidSubString.substring(0, endIndex);
	                    System.out.println("ocid 값: " + ocid);
	                }
	            }

	            in.close();

	            System.out.println(response.toString());


	        } catch (Exception exception) {
	            System.out.println(exception);
	        }
	        return new ResponseEntity<>(ocid, HttpStatus.OK);
	    }
	
	/* 캐릭터 basic 조회 */
	@RequestMapping(value = "/basicApi/{charId}", method = RequestMethod.GET)
	public String basicApi(@PathVariable String charId) {
		logger.info("넘어오냐=====");
		logger.info("charId :"+charId);
		String ocid = null;
		
		
	     // 어제 날짜 구하기
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); // 현재 날짜에서 하루를 빼줍니다.
        Date yesterday = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String yesterdayDate = dateFormat.format(yesterday);
		try {
	            String characterName = URLEncoder.encode(charId, "UTF-8");
	            String urlString = "https://open.api.nexon.com/maplestory/v1/character/basic?ocid=" + charId + "&date=" + yesterdayDate;
	            System.out.println("urlString: " + urlString);
	            URL url = new URL(urlString);

	        
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");
	            connection.setRequestProperty("x-nxopen-api-key", API_KEY);

	            int responseCode = connection.getResponseCode();

	            BufferedReader in;
	            if (responseCode == 200) {
	            	in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            } else {
	                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
	            }

	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }

	            // StringBuffer를 String으로 변환
	            String responseString = response.toString();
	            in.close();

	            System.out.println(response.toString());
			} catch (Exception exception) {
	            System.out.println(exception);
	        }
	        return "";
	    }
	
}
