package cars24.DocumentCreator.service.validator;

import org.springframework.stereotype.*;
import java.util.*;

@Service
public class GenericValidation {

    public void validate(Map userRequest) throws Exception {
        //Condition if user is Admin
        if(Objects.nonNull(userRequest.get("user")) && ((String)userRequest.get("user")).equals("Admin")){
            return;
        }
        //Condition If User Tenant Id and Template Tenant Id does not Match
        /*TODO : Write condition for this.
        * */

        throw new Exception("Invalid User");
    }
}
