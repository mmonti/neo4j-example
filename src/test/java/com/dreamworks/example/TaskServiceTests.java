package com.dreamworks.example;

import com.dreamworks.example.config.Neo4jEmbeddedConfig;
import com.dreamworks.example.model.Task;
import com.dreamworks.example.model.TaskVersion;
import com.dreamworks.example.model.request.TaskPayload;
import com.dreamworks.example.model.request.TaskVersionPayload;
import com.dreamworks.example.service.TaskService;
import com.dreamworks.example.service.TaskVersionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@ActiveProfiles("test")
@ContextConfiguration(classes = { Neo4jEmbeddedConfig.class })
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskVersionService taskVersionService;

    @Autowired
    private SessionFactory sessionFactory;

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {
        // = Clear the graph with every test
        final Session session = sessionFactory.openSession();
        session.purgeDatabase();
    }

    @Test
    public void testIssue1() throws Exception {
        // = We create a task.
        Task task = taskService.create(new TaskPayload("task"));

        // = We create a task version and we establish the relation with the task.
        TaskVersion version1 = taskService.create(task.getId(), null, new TaskVersionPayload());

        // = We create a new task linked to the previous task version (version1) and also linked to the task.
        TaskVersion version2 = taskService.create(task.getId(), version1.getId(), new TaskVersionPayload());

        // ====== Asserts on created objects. ======= //

        // = Ensure parent is wired up correctly.
        Assert.assertEquals(version1, version2.getParent());

        // = Ensure the version 1 has only 1 children.
        Assert.assertEquals(1, version2.getParent().getChilds().size());

        // = Ensure the version 2 doesnt have any children.
        Assert.assertEquals(0, version2.getChilds().size());

        // These asserts are Ok cause we are inspecting the objects we have in memory.
        // Lets see what was saved in the Store...

        // ======= Retrieve what is in the store and Asserts again with the same checks. ======= //

        // = Get the task by ID (This is our generated ID and not the @GraphId).
        Task storedTask = taskService.get(task.getId());

        // = Ensure the the task has 2 versions
        Assert.assertTrue(storedTask.hasVersions());
        Assert.assertEquals(2, storedTask.getVersions().size());

        // = Lets get what is in the store.
        TaskVersion storedTaskVersion2 = taskVersionService.get(version2.getId());

        // = Check that parent of version2 has a child.
        Assert.assertEquals(1, storedTaskVersion2.getParent().getChilds().size());

        // FIXME: 6/27/17
        // This last assert makes the test fail and we cannot find the root cause of the issue. If we inspect the query that is executed
        // in line 86 we see:
        // MATCH (n:`VERSION`) WHERE n.`id` = "d031ffb5-a24c-4007-b029-da14604d62ca@taskVersion" WITH n MATCH p=(n)-[*0..2]-(m) RETURN p, ID(n)
        // If we c&p that statement in the browser we can see the results are correct and the nodes contains the relations we expect.

        // = Ensure there are no children versions in version2
        Assert.assertEquals(0, storedTaskVersion2.getChilds().size());
    }

    @Test
    public void testIssue2() throws Exception {
        // = We create a task.
        Task task = taskService.create(new TaskPayload("task"));

        // = We create a task version and we establish the relation with the task.
        TaskVersion version1 = taskService.create(task.getId(), null, new TaskVersionPayload());

        // = We create a new task linked to the previous task version (version1) and also linked to the task.
        TaskVersion version2 = taskService.create(task.getId(), version1.getId(), new TaskVersionPayload());

        // = We create a new task linked to the previous task version (version2) and also linked to the task.
        TaskVersion version3 = taskService.create(task.getId(), version2.getId(), new TaskVersionPayload());

        // ====== Asserts on created objects. ======= //

        // = Ensure parent is wired up correctly.
        Assert.assertEquals(version2, version3.getParent());

        // = Ensure the version 2 and 3 has only 1 children.

        // FIXME: 6/27/17
        // version1 has 2 children instead of 1 (commenting out this assert)
        // Assert.assertEquals(1, version2.getParent().getChilds().size());

        // FIXME: 6/27/17
        // version2 has 2 children instead of 1 (commenting out this assert)
        // Assert.assertEquals(1, version3.getParent().getChilds().size());

        // = Ensure the version 3 doesn't have any children.
        Assert.assertEquals(0, version3.getChilds().size());

        // These asserts are Ok cause we are inspecting the objects we have in memory.
        // Lets see what was saved in the Store...

        // ======= Retrieve what is in the store and Asserts again with the same checks. ======= //

        // = Get the task by ID (This is our generated ID and not the @GraphId).
        Task storedTask = taskService.get(task.getId());

        // = Ensure the the task has 3 versions
        Assert.assertTrue(storedTask.hasVersions());
        Assert.assertEquals(3, storedTask.getVersions().size());

        // = Lets get what is in the store.
        TaskVersion storedTaskVersion3 = taskVersionService.get(version3.getId());

        // = Check that parent of version3 has a child.
        Assert.assertEquals(1, storedTaskVersion3.getParent().getChilds().size());

        // FIXME: 6/27/17
        // = Ensure there are no children versions in version2
        // Assert.assertEquals(0, storedTaskVersion3.getChilds().size());
    }

    @Test
    public void testIssue3() throws Exception {
        // = We create a task.
        Task task = taskService.create(new TaskPayload("task"));

        // = We create a task version and we establish the relation with the task.
        TaskVersion version1 = taskService.create(task.getId(), null, new TaskVersionPayload());

        // = We create a new task linked to the previous task version (version1) and also linked to the task.
        TaskVersion version2 = taskService.create(task.getId(), version1.getId(), new TaskVersionPayload());

        // = We create a new task linked to the previous task version (version2) and also linked to the task.
        TaskVersion version3 = taskService.create(task.getId(), version2.getId(), new TaskVersionPayload());

        // = We create a new task linked to the previous task version (version3) and also linked to the task.
        TaskVersion version4 = taskService.create(task.getId(), version3.getId(), new TaskVersionPayload());

        // ====== Asserts on created objects. ======= //

        // = Ensure parent is wired up correctly.
        Assert.assertEquals(version3, version4.getParent());

        // = Ensure the version 2 and 3 has only 1 children.

        // FIXME: 6/27/17
        // version2 now has 2 children instead of 1 (commenting out this assert)
        // Assert.assertEquals(1, version3.getParent().getChilds().size());

        // FIXME: 6/27/17
        // version3 now has 2 children instead of 1 (commenting out this assert)
        // Assert.assertEquals(1, version4.getParent().getChilds().size());

        // = Ensure the version 4 doesn't have any children.
        Assert.assertEquals(0, version4.getChilds().size());

        // These asserts are Ok cause we are inspecting the objects we have in memory.
        // Lets see what was saved in the Store...

        // ======= Retrieve what is in the store and Asserts again with the same checks. ======= //

        // = Get the task by ID (This is our generated ID and not the @GraphId).
        Task storedTask = taskService.get(task.getId());

        // = Ensure the the task has 3 versions
        Assert.assertTrue(storedTask.hasVersions());
        Assert.assertEquals(4, storedTask.getVersions().size());

        // = Lets get what is in the store.
        TaskVersion storedTaskVersion4 = taskVersionService.get(version4.getId());

        // = Check that parent of version4 has a child.
        // FIXME: 6/27/17
        // version3 now has 2 children instead of 1 (commenting out this assert)
        // Assert.assertEquals(1, storedTaskVersion4.getParent().getChilds().size());

        // = Ensure there are no children versions in version4
        // FIXME: 6/27/17
        // version4 shouldn't have any children.
        Assert.assertEquals(0, storedTaskVersion4.getChilds().size());
    }
}
