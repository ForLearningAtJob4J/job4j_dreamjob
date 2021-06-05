package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {

    private static final MemStore INST = new MemStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    private final Map<Integer, City> cities = new ConcurrentHashMap<>();

    private static final AtomicInteger POST_ID = new AtomicInteger(3);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(3);
    private static final AtomicInteger USER_ID = new AtomicInteger(1);

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job", "blah-blah-blah", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", "aw-aw-aw", LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job", "oh-oh-oh", LocalDateTime.now()));

        City moscow = new City(1, "Moscow");
        City tokyo = new City(2, "Tokyo");
        City newYork = new City(3, "New York");

        cities.put(0, new City(0, "City is hidden"));
        cities.put(1, moscow);
        cities.put(2, tokyo);
        cities.put(3, newYork);

        candidates.put(1, new Candidate(1, "Junior Java", moscow));
        candidates.put(2, new Candidate(2, "Middle Java", tokyo));
        candidates.put(3, new Candidate(3, "Senior Java", newYork));

        User admin = new User();
        admin.setId(1);
        admin.setName("Admin");
        admin.setEmail("root@local");
        admin.setPassword("root");
        users.put(1, admin);
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public Collection<User> findAllUsers() {
        return users.values();
    }

    @Override
    public Collection<City> findAllCities() {
        return null;
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            user.setId(USER_ID.incrementAndGet());
        }
        users.put(user.getId(), user);
    }

    @Override
    public void delete(Candidate candidate) {
        candidates.remove(candidate.getId());
    }

    @Override
    public void delete(Post post) {
        posts.remove(post.getId());
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    @Override
    public Post findPostById(int id) {
        return posts.get(id);
    }

    @Override
    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    @Override
    public City findCityById(int id) {
        return cities.get(id);
    }

    @Override
    public User findUserById(int id) {
        return users.get(id);
    }

    public User findUserByEmail(String email) {
        return users.entrySet().stream()
                .filter(u -> u.getValue().getEmail().equals(email))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);
    }
}