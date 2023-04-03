package com.estore.api.estoreapi.persistence;

import java.util.Map;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.util.TreeMap;

import com.estore.api.estoreapi.model.Review;

@Component
public class ReviewFileDAO implements ReviewDAO {
    private static final Logger LOG = Logger.getLogger(ReviewFileDAO.class.getName());

    /** Maps ids to their corresponding {@link Review} */
    Map<Integer, Review> reviewMap;

    /** JSON serializer/deserializer */
    private ObjectMapper objectMapper;

    /** Next id to assign */
    private static int nextId;

    private String filename;

    /**
     * Build the {@link ReviewFileDAO}
     * 
     * @param filename Filename to store the data in
     * @param objectMapper JSON serializer/deserializer
     * 
     * @throws IOException If there is an error reading the file
     */
    public ReviewFileDAO(@Value("${review.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Gets next id and increments it
     * 
     * Syncronized to prevent multiple threads from accessing nextId concurrently
     * 
     * @return The next id to assign
     */
    private synchronized static int getNextId() {
        return nextId++;
    }

    /**
     * Gets array of {@link Review reviews} from the file
     * 
     * @return Array of stored {@link Review reviews}, or empty array if none
     */
    private Review[] getReviewsArray() {
        ArrayList<Review> reviewList = new ArrayList<>();
        for(Review currentReview: reviewMap.values()) {
            reviewList.add(currentReview);
        }
        Review[] returnValue = new Review[reviewList.size()];
        reviewList.toArray(returnValue);
        return returnValue;
    }

    /**
     * Saves {@link Review} to the DAO file
     * 
     * @throws IOException If there is an error writing to the file
     */
    private void save() throws IOException{
        Review[] allReviews = getReviewsArray();

        // Uses object mapper to convert to json and write to file
        objectMapper.writeValue(new File(filename), allReviews);
    }

    /**
     * Load all {@link Review} from the DAO file and store in the map
     * 
     * @throws IOException If there is an error reading the file
     */
    private void load() throws IOException {
        reviewMap = new TreeMap<>();
        nextId = 0;

        Review[] serializedReviews = objectMapper.readValue(new File(filename), Review[].class);

        // Add all orders and keep track of the greatest id
        for (Review review: serializedReviews) {
            reviewMap.put(review.getId(), review);
            if (review.getId() > nextId) {
                nextId = review.getId();
            }
        }

        // Incremement id again so it is ready to return the next id
        getNextId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Review[] getReviews() {
        // Synchronize on ReviewMap so that it can't be modified while being read
        synchronized(reviewMap) {
            return getReviewsArray();
        }
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public Review getReview(int id) {
        synchronized(reviewMap) {
            return reviewMap.get(id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Review createReview(Review review) throws IOException {
        synchronized(reviewMap) {
            Review tmpRev = new Review(getNextId(), review.getProductId(), review.getCustomerUser(), 
                            review.getStars(), review.getReviewContent(), review.getDateTime(), review.getCustomerUsername(),
                            review.getProducts());
            
            // Add to map and save to DAO
            reviewMap.put(tmpRev.getId(), tmpRev);
            save();

            return tmpRev;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteReview(int id) throws IOException {
        synchronized (reviewMap) {
            if (!reviewMap.containsKey(id)) {
                return false;
            }
            else {
                reviewMap.remove(id);
                save();
                return true;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Review updateReview(Review review) throws IOException {
        synchronized(reviewMap){
            if(!reviewMap.containsKey(review.getId())){
                return null;
            }

            reviewMap.put(review.getId(), review);
            save();

            return review;
        }
    }

}