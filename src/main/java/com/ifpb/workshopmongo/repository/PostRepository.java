package com.ifpb.workshopmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ifpb.workshopmongo.domain.Post;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    //consulta com titulo    List<Post> findByTitleContainingIgnoreCase(String text);

    @Query("{ 'title': { $regex: ?0, $options: 'i' } }") // 0 primeiro parametro, i igonrar case sensitive
    List<Post> searchTitle(String text);

    @Query("{ $and: [ { date: {$gte: ?1} }, { date: { $lte: ?2} } ," + //data entre data min e max
            " { $or: [ { 'title': { $regex: ?0, $options: 'i' } }," + // texto pertence ao titulo, corpo ou comentario
            " { 'body': { $regex: ?0, $options: 'i' } }, { 'comments.text': { $regex: ?0, $options: 'i' } } ] } ] }") //corpo ou comentario
    List<Post> fullSearch(String text, Date minDate, Date maxDate);
}