package io.glide.boot.repository;

import io.glide.boot.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

}
