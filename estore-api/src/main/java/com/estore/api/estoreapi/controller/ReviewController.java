package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Review;
import com.estore.api.estoreapi.persistence.ReviewDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controls the REST requests and responses for a Review
 * 
 *@author Alexandria Pross
 */

@RestController
@RequestMapping("reviews")
public class ReviewController {

    private static final Logger LOG = Logger.getLogger(ReviewController.class.getName());
    private ReviewDAO reviewDAO;

    /**
     * Construct a REST API controller for a {@link Review}
     * 
     * @param reviewDAO Data access object (Ex. FileDAO)
     */
    public ReviewController(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Review review} for the given id
     * 
     * @param id The id used to locate the {@link Review review}
     * 
     * @return ResponseEntity with {@link Review review} object and the HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable int id) {
        LOG.info("GET /reviews/" + id);
        try {
            Review review = reviewDAO.getReview(id);
            if (review != null)
                return new ResponseEntity<Review>(review, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a {@linkplain Review review} for all ids in the array
     * 
     * @param id The id used to locate the {@link Review review}
     * 
     * @return ResponseEntity with {@link Review review} object and the HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
     @GetMapping("")
     public ResponseEntity<Review[]> getReviews() {
 
         LOG.info("GET /reviews");
         try {
             Review[] reviews = reviewDAO.getReviews();
             if(reviews != null)
                 return new ResponseEntity<Review[]>(reviews, HttpStatus.OK);
             else
                 return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
         }
         catch(IOException e) {
             LOG.log(Level.SEVERE,e.getLocalizedMessage());
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
         }
         
     }

     /**
     * Creates a {@linkplain Review review} with review object
     * 
     * @param review - the {@link Review review} to create
     * @return ResponseEntity with created {@link Review review} object and HTTP
     *         status of CREATED
     *         ResponseEntity with HTTP status of CONFLICT if {@link Review
     *         review} object already exists
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        LOG.info("POST /reviews " + review);
        try {

            Review result = reviewDAO.createReview(review);

            if (result != null) {
                return new ResponseEntity<Review>(result, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Review review} with the given id
     * 
     * @param id - the id of the {@link Review review} to delete
     * @return ResponseEntity HTTP status of OK if deleted
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable int id) {
        LOG.info("Delete /reviews/" + id);
        try {
            if (reviewDAO.deleteReview(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Review review} with the provided {@linkplain Review review} object, if it exists
     *
     * @param review The {@link Review review} to update
     *
     * @return ResponseEntity with updated {@link Review review} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Review> updateReview(@RequestBody Review review) {
        LOG.info("PUT /reviews " + review);
        try {
            if(reviewDAO.updateReview(review) != null)
                return new ResponseEntity<Review>(review,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
