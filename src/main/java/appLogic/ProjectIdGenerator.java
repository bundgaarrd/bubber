package appLogic;
import java.time.Year;

public class ProjectIdGenerator {
    private Integer currentSequence = 1;
    public ProjectIdGenerator(Integer currentSequence){
        this.currentSequence = currentSequence;
    }


    public Integer getYear(){
        int currentYear = Year.now().getValue();
        return currentYear;

    }
   
    public String generateId(){
        currentSequence.toString();
        String newSequence =  String.format("%03d" , currentSequence);
        String year = getYear().toString();
        String genID = year.concat("-" + newSequence);
        currentSequence++;
        return genID;
        
    }












}