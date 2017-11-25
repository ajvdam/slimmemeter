package smarthome.slimmemeter.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Telegram {

   private LocalDateTime dateTime;	
   private int vermogen;
   private double verbruikTarief1;
   private double verbruikTarief2;
   
}
