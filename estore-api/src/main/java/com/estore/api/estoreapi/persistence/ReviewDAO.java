package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Review;

public interface ReviewDAO {
/**
 * Interface for saving a Review to a data store
 * 
 * @author Alexandria Pross
 */

    /**
     * Retrieves all {@link Review review} from the data store
     * 
     * @return Array of {@link Review review} objects, array may be empty
     * @throws IOException If the data store cannot be accessed
     */

    Review[] getReviews() throws IOException;

    /**
     * Retrieves {@link Review review} from the data store
     * 
     * @return Array of {@link Review review} objects, array may be empty
     * @throws IOException If the data store cannot be accessed
     */

    Review getReview(int id) throws IOException;

    /**
     * Finds all {@link Review review} that match the given text
     * 
     * @param text Text that should match all returned
     * @return Array of matching {@link Review reviews}, array may be empty
     * @throws IOException If the data store cannot be accessed
     */

    Review createReview(Review review) throws IOException;

     /**
     * Deletes a {@link Review review} from the data store
     * 
     * @param id ID of the {@link Review review} to delete
     * @return True if the ID product was deleted false if not
     * @throws IOException If the data store cannot be accessed
     */

     boolean deleteReview(int id) throws IOException;

    /**
     * Updates a {@link Review review} in the data store
     * 
     * @param review Updated {@link Review review} to save
     * @return {@link Review review} (null if not able to be updated)
     * @throws IOException If the data store cannot be accessed
     */
    Review updateReview(Review review) throws IOException;
    
}

