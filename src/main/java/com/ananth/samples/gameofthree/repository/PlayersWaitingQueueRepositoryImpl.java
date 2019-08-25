package com.ananth.samples.gameofthree.repository;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Waiting players queue.
 */
@Repository("playersWaitingQueueRepository")
public class PlayersWaitingQueueRepositoryImpl implements PlayersWaitingQueueRepository {

    private Set<String> queue = Collections.synchronizedSet(new LinkedHashSet<String>());

    @Async
    @Override
    public CompletableFuture<Boolean> addPlayer(final String sessionId) {

        synchronized (queue) {

            queue.add(sessionId);
        }
        return CompletableFuture.completedFuture(true);
    }

    @Override
    public Optional<String> findWaitingRival(String starterPlayerSession) {

        String receiver = null;
        synchronized (this.queue) {

            if (queue.size() >= 2) {

                if (queue.remove(starterPlayerSession)) {
                    Iterator<String> iterator = queue.iterator();
                    receiver = iterator.next();
                    queue.remove(receiver);
                }
            }
        }
        return Optional.ofNullable(receiver);
    }
}
