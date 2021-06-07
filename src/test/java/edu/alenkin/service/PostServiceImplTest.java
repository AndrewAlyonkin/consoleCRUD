package edu.alenkin.service;

import edu.alenkin.model.Post;
import edu.alenkin.model.PostStatus;
import edu.alenkin.model.Writer;
import edu.alenkin.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostServiceImplTest {
    @Mock
    PostRepository postMock;
    @InjectMocks
    PostServiceImpl postService = new PostServiceImpl();
    private ArgumentCaptor<Long> postIdCaptor;
    private ArgumentCaptor<Long> writerIdCaptor;
    private ArgumentCaptor<Post> postCaptor;
    private ArgumentCaptor<Writer> writerCaptor;
    private long testPostId;
    private long testWriterId;
    private Post testPost;
    private Writer testWriter;
    private List<Post> testPostList;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(postMock);
        testPostId = 10L;
        testWriterId = 100L;
        testPost = new Post(testPostId, "testContent", LocalDateTime.now());
        testPost.setStatus(PostStatus.DELETED);

        testWriter = new Writer(testWriterId, "testName", "testSoName");
        testWriter.addPost(testPost);
        postIdCaptor = ArgumentCaptor.forClass(Long.class);
        writerIdCaptor = ArgumentCaptor.forClass(Long.class);
        postCaptor = ArgumentCaptor.forClass(Post.class);
        writerCaptor = ArgumentCaptor.forClass(Writer.class);
        testPostList = List.of(testPost);
    }

    @Test
    void removePostTest() {
        postService.remove(testPostId);
        Mockito.verify(postMock).delete(postIdCaptor.capture());
        assertEquals(testPostId, postIdCaptor.getValue());
    }

    @Test
    void addPostTest() {
        Mockito.when(postMock.save(testPost, testWriterId)).thenReturn(testPostId);
        Long id = postService.add(testPost, testWriterId);
        Mockito.verify(postMock).save(postCaptor.capture(), writerIdCaptor.capture());
        assertEquals(testPost, postCaptor.getValue());
        assertEquals(testWriterId, writerIdCaptor.getValue());
        assertEquals(testPostId, id);
    }

    @Test
    void updatePostTest() {
        Mockito.when(postMock.save(testPost, testWriterId)).thenReturn(testPostId);
        Long id = postService.update(testPost, testWriterId);
        Mockito.verify(postMock).save(postCaptor.capture(), writerIdCaptor.capture());
        assertEquals(testPost, postCaptor.getValue());
        assertEquals(testWriterId, writerIdCaptor.getValue());
        assertEquals(testPostId, id);
    }

    @Test
    void getPostTest() {
        Mockito.when(postMock.get(testPostId)).thenReturn(testPost);
        Post currentPost = postService.get(testPostId);
        Mockito.verify(postMock).get(postIdCaptor.capture());
        assertEquals(testPostId, postIdCaptor.getValue());
        assertEquals(testPost, currentPost);
    }

    @Test
    void getByWriterIdTest() {
        Mockito.when(postMock.getByWriterId(testWriterId)).thenReturn(testPostList);
        List<Post> postsList = postService.getByWriterId(testWriterId);
        Mockito.verify(postMock).getByWriterId(writerIdCaptor.capture());
        assertEquals(testPostList, postsList);
        assertEquals(testWriterId, writerIdCaptor.getValue());
    }
}