package cars24.DocumentCreator.service.validator;

import cars24.DocumentCreator.utility.Constants;
import org.springframework.stereotype.*;
import java.util.*;

@Service
public class UserValidation {

    public void validate(Map userRequest) throws Exception {
        //Condition if user is Admin
        if(Objects.nonNull(userRequest.get(Constants.INPUT_FIELDS.USER)) && ((String)userRequest.get(Constants.INPUT_FIELDS.USER)).equals("Admin")){
            return;
        }
        //Condition If User Tenant Id and Template Tenant Id does not Match
        /*TODO : Write condition for this.
        * */

        throw new Exception("Invalid User");
    }

    public void validateAdmin(Map userRequest) throws Exception {
        if(Objects.nonNull(userRequest.get(Constants.INPUT_FIELDS.USER)) && ((String)userRequest.get(Constants.INPUT_FIELDS.USER)).equals("Admin")){
            return;
        }
        throw new Exception("Invalid User");
    }
}
