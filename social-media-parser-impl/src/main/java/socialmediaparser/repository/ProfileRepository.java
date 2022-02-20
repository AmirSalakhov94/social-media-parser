package socialmediaparser.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import socialmediaparser.entity.Profile;

public interface ProfileRepository extends CassandraRepository<Profile, String> {
}
