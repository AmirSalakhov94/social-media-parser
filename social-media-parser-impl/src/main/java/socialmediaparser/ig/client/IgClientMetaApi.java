package socialmediaparser.ig.client;

import dto.ig.ClientMetaData;

import java.util.Set;

public interface IgClientMetaApi {

    Set<ClientMetaData> getListClientMetaData(int count);
}
