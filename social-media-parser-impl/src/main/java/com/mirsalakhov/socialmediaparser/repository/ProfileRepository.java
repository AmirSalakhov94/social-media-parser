package com.mirsalakhov.socialmediaparser.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import com.mirsalakhov.socialmediaparser.entity.Profile;

public interface ProfileRepository extends CassandraRepository<Profile, String> {
}
