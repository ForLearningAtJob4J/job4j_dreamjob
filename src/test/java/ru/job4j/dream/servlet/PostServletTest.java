package ru.job4j.dream.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest {

    @Test
    public void whenAddPostThenStoreIt() throws IOException {
        Store memStore = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(memStore);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("op")).thenReturn("edit");
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("CEO");
        when(req.getParameter("description")).thenReturn("some description");
        new PostServlet().doPost(req, resp);
        var posts = memStore.findAllPosts();
        assertTrue(posts.stream().anyMatch(el -> el.getName().equals("CEO")));
    }

    @Test
    public void whenGetForAddOrEditThenForward() throws ServletException, IOException {
        Store memStore = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(memStore);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher("WEB-INF/post/edit.jsp")).thenReturn(dispatcher);

        new PostServlet().doGet(req, resp);
        verify(dispatcher).forward(req, resp);
    }

    @Test
    public void whenGetForDelThenRedirect() throws ServletException, IOException {
        Store memStore = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(memStore);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter("op")).thenReturn("del");
        when(req.getParameter("id")).thenReturn("1");
        HttpServletResponse resp = mock(HttpServletResponse.class);

        new PostServlet().doGet(req, resp);
        verify(resp).sendRedirect(req.getContextPath() + "/posts.do");
        assertNull(memStore.findPostById(1));
    }
}