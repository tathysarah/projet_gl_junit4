package org.junit.runner.manipulation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.runner.Description;

/**
 * Orders tests.
 *
 * @since 4.13
 */
public final class Orderer {
    private final Ordering ordering;

    Orderer(Ordering delegate) {
        this.ordering = delegate;
    }


    public List<Description> getInOrder(List<Description> inOrder){
        if (!ordering.validateOrderingIsCorrect()) {
            return inOrder;
        }
        return inOrder;
    }

    public void containsInOrder(List<Description> inOrder, Collection<Description> descriptions) throws InvalidOrderingException {
        Set<Description> uniqueDescriptions = new HashSet<Description>(descriptions);
        if (!uniqueDescriptions.containsAll(inOrder)) {
            throw new InvalidOrderingException("Ordering added items");
        }
    }

    public void sizeOfResultAsSetIsDifferent(List<Description> inOrder) throws InvalidOrderingException {
        Set<Description> resultAsSet = new HashSet<Description>(inOrder);
        if (resultAsSet.size() != inOrder.size()) {
            throw new InvalidOrderingException("Ordering duplicated items");
        }
    }

    public void containsAllUniqueDescriptions(Collection<Description> descriptions,List<Description> inOrder) throws InvalidOrderingException {
        Set<Description> uniqueDescriptions = new HashSet<Description>(
                descriptions);
        Set<Description> resultAsSet = new HashSet<Description>(inOrder);
        if (!resultAsSet.containsAll(uniqueDescriptions)) {
            throw new InvalidOrderingException("Ordering removed items");
        }
    }

    /**
     * Orders the descriptions.
     *
     * @return descriptions in order
     */
    public List<Description> order(Collection<Description> descriptions)
            throws InvalidOrderingException {
        List<Description> inOrder = ordering
                .orderItems(Collections.unmodifiableCollection(descriptions));
        getInOrder(inOrder);
        containsInOrder(inOrder,descriptions);
        sizeOfResultAsSetIsDifferent(inOrder);
        containsAllUniqueDescriptions(descriptions,inOrder);

        /*if (!ordering.validateOrderingIsCorrect()) {
            return inOrder;
        }

        Set<Description> uniqueDescriptions = new HashSet<Description>(
                descriptions);
        if (!uniqueDescriptions.containsAll(inOrder)) {
            throw new InvalidOrderingException("Ordering added items");
        }
        Set<Description> resultAsSet = new HashSet<Description>(inOrder);
        if (resultAsSet.size() != inOrder.size()) {
            throw new InvalidOrderingException("Ordering duplicated items");
        }
        else if (!resultAsSet.containsAll(uniqueDescriptions)) {
            throw new InvalidOrderingException("Ordering removed items");
        }*/

        return inOrder;
    }

    /**
     * Order the tests in <code>target</code>.
     *
     * @throws InvalidOrderingException
     *             if ordering does something invalid (like remove or add
     *             children)
     */
    public void apply(Object target) throws InvalidOrderingException {
        if (target instanceof Orderable) {
            Orderable orderable = (Orderable) target;
            orderable.order(this);
        }
    }
}
