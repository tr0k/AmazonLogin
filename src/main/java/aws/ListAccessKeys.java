package aws;

/**
 * Created by tr0k on 2017-10-12.
 */

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.AccessKeyMetadata;
import com.amazonaws.services.identitymanagement.model.ListAccessKeysRequest;
import com.amazonaws.services.identitymanagement.model.ListAccessKeysResult;

import java.util.ArrayList;
import java.util.List;

/**
 * List all access keys associated with an IAM user
 */
public class ListAccessKeys {

    private String user;

    public ListAccessKeys(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> listAccessKeys(){
        final AmazonIdentityManagement iam =
                AmazonIdentityManagementClientBuilder.defaultClient();

        boolean done = false;

        ArrayList<String> accessKeys = null;
        while (!done) {
            ListAccessKeysRequest request = new ListAccessKeysRequest()
                    .withUserName(user);

            ListAccessKeysResult response = iam.listAccessKeys(request);
            accessKeys = new ArrayList<String>();
            for (AccessKeyMetadata metadata :
                    response.getAccessKeyMetadata()) {
                accessKeys.add(metadata.getAccessKeyId());
            }

            request.setMarker(response.getMarker());

            if (!response.getIsTruncated()) {
                done = true;
            }
        }

        return accessKeys;
    }


}