package ru.aao.geoproxyservice.types.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Data
@EqualsAndHashCode
public class LogRecord implements Serializable {

	private static final long serialVersionUID = -5413175379218938232L;

	private String traceId;
    private String spanId;
    private String system;
    private String host;
    private String component;
    private String operation;
    private Direction direction;
    private String step;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private DateTime timestamp;
    private Object data;

    public LogRecord() {
    	 try {
    		 this.host = InetAddress.getLocalHost().getHostName();
         } catch (UnknownHostException e) {
             this.host = "unknown";
         }
    }
 
}


