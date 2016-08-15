import java.util.Collections;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public class intervalScheduling {
  public static void main (String [] args) {
    // inputs:
    int [] stime_array = new int []  {1,   10,   5,    4,   12,   8,    7,    6,   14,   20};
    int [] ftime_array = new int []  {4,   13,   11,   5,   14,   12,   10,   8,   19,   23};
    // assuming no ties in finishing times
    // if there were ties we have to use List instead
    Hashtable<Integer, Integer> ftimes = new Hashtable<Integer, Integer>();
    // we have to use hashtable to keep track of task id's
    ftimes = hashtablePopulate(ftimes, ftime_array);
    ArrayList<Integer> stimes = new ArrayList<Integer>();

    /* Simple SINGLE Resource Interval Scheduling: */
    // solve the simple scheduling problem for a SINGLE resource (resource sharing)
    ArrayList<Integer> accepted_task_ids = new ArrayList<Integer>();
    accepted_task_ids = simpleSchedulingProblem (ftimes, stime_array, stimes, accepted_task_ids);
    // output the results:
    System.out.println("\n"+"One of the possible optimal sharing strategy (interval scheduling) is:");
    for(Integer ind : accepted_task_ids) {
      System.out.println(ind + " => [" + stime_array[ind] + " : " + ftime_array[ind] + "]");
    }
    System.out.println("The maximum number of tasks that can be accomplished using our greedy strategies is: " + accepted_task_ids.size() + "\n");

    // inputs:
    int [] st_array = new int []       {1,   10,   5,    4,   12,   8,    7,    6,   14,   20};
    int [] ft_array = new int []       {4,   13,   8,    5,   14,   11,   15,   8,   19,   23};
    Hashtable<Integer, ArrayList<Integer>> ft = new Hashtable<Integer, ArrayList<Integer>>();
    // we have to use hashtable to keep track of task id's and ftimes
    ft = hashtableListPopulate(ft, ft_array);
    // we use st to keep track of ALL the requests (Accepted and non-Accepted)
    ArrayList<Integer> st = new ArrayList<Integer>();

    /* Finishing times can be NOT distinct (ties are allowed) */
    // solve the simple scheduling problem for a SINGLE resource (resource sharing)
    // we use accepted_task_ids to keep track of all Accepted requests
    accepted_task_ids = new ArrayList<Integer>();
    // Find the list of accepted requests such that it maximizes the number of FINISHED COMPATIBLE tasks.
    accepted_task_ids = intermediateSchedulingProblem (ft, st_array, st, accepted_task_ids);
    // output the results:
    System.out.println("One of the possible optimal sharing strategy (interval scheduling) is:");
    for(Integer ind : accepted_task_ids) {
      System.out.println(ind + " => [" + stime_array[ind] + " : " + ftime_array[ind] + "]");
    }
    System.out.println("The maximum number of tasks that can be accomplished using our greedy strategies is: " + accepted_task_ids.size() +"\n");
    /*
    // Hashtable with Lists as its values
    int [] time_array = new int []  {4,   13,   11,   5,   11,   12,   13,   8,   4,   23};
    Hashtable<Integer, ArrayList<Integer>> ht = new Hashtable<Integer, ArrayList<Integer>>();
    ht = hashtableListPopulate(ht, time_array);
    ArrayList<Integer> lt = new ArrayList<Integer>(ht.keySet());
    for(Integer key : lt) {
      System.out.print(key + ": [");
      for(int i=0; i< ht.get(key).size(); i++){
        System.out.print(ht.get(key).get(i));
        if (i<ht.get(key).size()-1)
          System.out.print(" ");
      }
      System.out.println("]");
    }*/
    
    // inputs: each interval request has a value associated with it:
    int [] task_lengths = new int [ft_array.length];
    float [] task_weights = new float []{0.85f,2.4f,1.3f,1.1f,1.5f, 1.4f, 1.2f,3.0f,3.2f, 2.8f};
    for(int i=0; i < ft_array.length; i++)  task_lengths[i] = ft_array[i] - st_array[i]; 
    // task lengths are keys and task ids are values in 
    // len_ht hashtable: We do this in order to keep track of 
    // the task ids after sort operation:
    Hashtable<Integer, ArrayList<Integer>> len_ht = new Hashtable<Integer, ArrayList<Integer>>();
    // we have to use hashtable to keep track of task id's and task length
    len_ht = hashtableListPopulate(len_ht, task_lengths);

    /* Weighted SINGLE Resource Interval Scheduling: */    
    // Goal: Our goal is to constitute a subset of compatible intervals
    // with a MAXIMAL total value
    // Compare to the simple scheduling at which we wanted to MAXIMIZE the
    // number of COMPLETED COMPATIBLE intervals (tasks), in this problem we
    // want to MAXIMIZE the total value of a subset of COMPLETED COMPATIBLE intervals (tasks)
    // NOTE: Obviously, sorting intervals based on their corresponding (associated) ftimes is
    // NOT a proper GREEDY RULE, since the objective function we're trying to optimize is not
    // the number of COMPLETED COMPATIBLE tasks, rather, the TOTAL VALUE of COMPLETED COMPATIBLE tasks.
    accepted_task_ids = new ArrayList<Integer>();
    // Find the list of COMPLETED COMPATIBLE tasks such that it maximizes TOTAL VALUE of COMPLETED COMPATIBLE tasks.
    accepted_task_ids = weightedSchedulingProblem (accepted_task_ids);
    // output the results:
    System.out.println("One of the possible optimal sharing strategy (interval scheduling) is:");
    for(Integer id : accepted_task_ids) {
      System.out.println(id + " => [" + stime_array[id] + " : " + ftime_array[id] + "]");
    }
    System.out.println("The maximum number of tasks that can be accomplished using our greedy strategies is: " + accepted_task_ids.size() +"\n");

    // inputs:
    int [] s_array = new int []  {1,   3,   1,    4,   3,   1,   7,    3,   2,   1, 1,   2,    3,    4,   6,   3,    4,   3,   2,   5};
    int [] f_array = new int []  {4,   8,   5,    5,   7,   3,  10,    8,   9,   3, 4,   12,   5,    10,  9,   15,   8,   8,   6,   13};
    // Hashtable of starting times:
    // using stimes as keys and using the 
    // task_ids as values in order to keep track of
    // task_ids, after sorting stimes:
    Hashtable<Integer, ArrayList<Integer>> s_ht = new Hashtable<Integer, ArrayList<Integer>>();
    s_ht = hashtableListPopulate(s_ht, s_array);

    /* Scheduling All intervals (MULTIPLE IDENTICAL resources): Interval Partitioning (Interval Coloring) */
    // Schedule all requests using as few resources as possible
    int resource_count = resourceCounter(s_ht, s_array, f_array); // compute optimal number of resources required to finish all tasks
    ArrayList<Integer> colors = new ArrayList<Integer>();
    for(int i=0; i<resource_count; i++)  colors.add(i+1);
    System.out.println("The minimum number of processors are needed to schedule all given tasks is: " + resource_count+"\n");
    // colored intervals is a hashtable with 
    // interval (task) id as keys, and their corresponding 
    // color (assigned resource id) as a value:
    Hashtable<Integer, Integer> colored_tasks = new Hashtable<Integer, Integer>();
    colored_tasks = intervalColoring(s_ht, s_array, f_array, colored_tasks, colors);
    // output:
    System.out.println("Here is an optimal coloring of given intervals:" + "\n");
    ArrayList<Integer> lt = new ArrayList<Integer>(colored_tasks.keySet());
    for(Integer key : lt) System.out.println("[" + key + " : " + colored_tasks.get(key) + "]");
    System.out.print("\n");

    // inputs: Singel resource, mutliple interval requests, each interval request has a deadline d_i
    // and require a resource for t_i period of time:
    int [] length_array = new int []  {1,   7,   5,    4,   3,    10,    9,    6,    14,   20};
    int [] deadline_array = new int []{4,   13,  8,    5,   14,   11,    15,   12,   22,   23};
    // keys in the schedule hashtable are task ids
    // and values are stime and ftime:
    Hashtable<Integer, ArrayList<Integer>> schedule = new Hashtable<Integer, ArrayList<Integer>>();
    schedule = minLatenessScheduling (0, length_array, deadline_array, schedule);

    /* Scheduling to Minimize the Maximum Lateness among all tasks' completion times: */
    // NOTE: Simplifying Assumption: There is no idle time (no gap between processing tasks)
    // Output: Allocating the resource to non-overlaping interval requests such that all 
    // tasks get their interval time and minimizes the max lateness among all tasks:
    // The output must be a set of [stime, ftime] for each interval:
    System.out.println("Here is an optimal interval scheduling that minimizes the latecy for compeleting given set of tasks:" + "\n");
    ArrayList<Integer> shedule_list = new ArrayList<Integer>(schedule.keySet());
    for(Integer task_id : shedule_list) System.out.println(task_id + ":=>" + "[" + schedule.get(task_id).get(0) + " : " + schedule.get(task_id).get(1) + "]" + " Min MAXIMAL Lateness is: " + schedule.get(task_id).get(2));
    System.out.print("\n");

  }
  
  /* Scheduling to Minimize the Maximum Lateness among all tasks' completion times: */
  // Simplifying Assumption(s): There is no idle time (no gap between processing tasks)
  public static Hashtable<Integer, ArrayList<Integer>> minLatenessScheduling (int start_time, int [] task_lengths, int [] task_deadlines, Hashtable<Integer, ArrayList<Integer>> schedule) {
    // put task deadline in the hashtable in order to keep
    // keep track of task ids after sorting deadlines or:
    Hashtable<Integer, ArrayList<Integer>> deadlines_ht = new Hashtable<Integer, ArrayList<Integer>>();
    // deadlines are keys in the deadlines_ht hashtable and
    // the corresponding value to each deadline is the list of 
    // task ids with that same deadline:
    deadlines_ht = hashtableListPopulate(deadlines_ht, task_deadlines); // O(n) to populate the hashtable

    // sort deadlines: tasks with earlier deadlines must be completed earlier
    // 1. create a list of deadlines from the deadlines_ht hashtable:
    ArrayList<Integer> deadlines_list = new ArrayList<Integer>(deadlines_ht.keySet()); // O(n) to populate the list
    // 2. sort the keys (deadlines):
    Collections.sort(deadlines_list); // O(nlogn) to sort

    // retrieve the list of task based on their sorted deadline:
    // 1. create a new ArrayList (sorted_task_list):
    ArrayList<Integer> sorted_task_list = new ArrayList<Integer>();
    // 2. add all tasks in sorted key list to the sorted task list:
    for (Integer deadline : deadlines_list) sorted_task_list.addAll(deadlines_ht.get(deadline)); // O(n) to populate the list
    
    // initialize the start time of all tasks
    int stime = start_time;
    int ftime;
    // initialize the Lateness
    int lateness = 0;
    // declare the output (timeline) Hashtable:
    ArrayList<Integer> timeline;
    // For all tasks that are sorted by their corresponding
    // deadline:
    for (Integer task_id : sorted_task_list) {
      timeline = new ArrayList<Integer>();
      // add stime to the schedule:
      timeline.add(stime);
      // compute the ftime wrt stime and task_length
      ftime = stime+task_lengths[task_id];
      // add ftime to the schedule
      timeline.add(ftime);
      // check if this assignment cause any LATENESS and if the
      // caused LATENESS is greater than previously computed ones:
      if(ftime - task_deadlines[task_id] > lateness) lateness = ftime - task_deadlines[task_id];
      // add the Min MAXIMAL lateness to the schedule
      timeline.add(lateness);
      // put the task and its timeline all together in the schedule hashtable
      schedule.put(task_id, timeline);
      // update the stime for the next job in the list
      // if there is a gap (or idle time) in between tasks:
      // stime = ftime + some slack in between finishing one job and starting the next one; 
      // if there is NO gap (or idle time) in between tasks:
      stime = ftime; 
    } // O(n) to populate the hashtable
    return schedule;
  } // O(nlogn) the time complexity of the algorithm

  /* Scheduling All intervals (MULTIPLE IDENTICAL resources): Interval Partitioning (Interval Coloring) */
  public static Hashtable<Integer, Integer> intervalColoring (Hashtable<Integer, ArrayList<Integer>> s_ht, int [] s_array, int [] f_array, Hashtable<Integer, Integer> colored_tasks, ArrayList<Integer> colors) {
    
    // make a list of stimes from the s_ht:
    ArrayList<Integer> st_list = new ArrayList<Integer>(s_ht.keySet());
    // sort the stimes in a list
    Collections.sort(st_list); // O(nlogn)
    
    // list of all task sorted by their stimes
    ArrayList<Integer> sorted_task_list = new ArrayList<Integer>();
    for ( Integer stime : st_list ) {
      sorted_task_list.addAll(s_ht.get(stime)); // Add all the task index corresponding to the given key in the s_ht hashtable
    }

    // keep track of unused colors (resources)
    ArrayList<Integer> avaiable_colors;
    // keep track of list of the precedent tasks
    List<Integer> tasks_sublist;
    // For each task in the sorted task list:
    for (Integer current_task_id : sorted_task_list) {
      // (NOTE: use the last index in case of ties)
      tasks_sublist = new ArrayList<Integer>(sorted_task_list.subList(0, sorted_task_list.lastIndexOf(current_task_id)));
      avaiable_colors = new ArrayList<Integer>(colors);
      // For each preceding task that overlaps the current task
      // remove the color of the preceding task from the list
      // of avaiable colors:
      for (Integer preceding_task_id : tasks_sublist ) {
        if ( f_array[preceding_task_id] >= s_array[current_task_id] && colored_tasks.get( preceding_task_id ) != null )
	  avaiable_colors.remove(colored_tasks.get( preceding_task_id ));
      }
      // if there still a color letf in the 
      // list of avaiable color:
      if( !avaiable_colors.isEmpty() ) {
        // assign that color to the current task
	colored_tasks.put(current_task_id, avaiable_colors.get(0));
        // remove the color from the list 
        // of avaiable colors:
        avaiable_colors.remove(0);
      }
    }
    return colored_tasks;
  }
  
  public static int resourceCounter (Hashtable<Integer, ArrayList<Integer>> stimes_ht, int [] stimes, int [] ftimes) {

    // sort tasks based on their stimes (stimes_ht keys):
    ArrayList<Integer> stimes_list = new ArrayList<Integer>(stimes_ht.keySet());
    Collections.sort(stimes_list); // sorted stimes O(nlogn)

    // create a task list
    ArrayList<Integer> sorted_task_list = new ArrayList<Integer>();
    // append tasks to the sorted task list
    // based on thier corresponding stime:
    for(Integer stime : stimes_list) sorted_task_list.addAll(stimes_ht.get(stime)); // O(n)

    // we need at least one resource
    int depth = 1;

    // Now we have to compute (count) the max number of 
    // overlaping tasks with each task at its
    // starting time:
    for (Integer current_task : sorted_task_list) {
      // we need at least one resource
      int res_count = 1;
      // check the finishing times of all preceding tasks to the current 
      // task and check how many tasks are overlaping:
      // (NOTE: use the last index in case of ties)
      for (Integer preceding_task : sorted_task_list.subList(0, sorted_task_list.lastIndexOf(current_task))) { 
	// increase the number of required resources for each
	// overlapping task
	if (ftimes[preceding_task] >= stimes[current_task]) res_count++;
      }

      // check the max number of required resources
      // with previous iteration, and update it if required
      if (res_count >= depth) depth = res_count;
    } // O(n^2)
    return depth;
  }

  /* Weighted SINGLE Resource Interval Scheduling: */
  // Goal: Our goal is to constitute a subset of compatible intervals
  // with a MAXIMAL total value
  // NOTE: Obviously, sorting intervals based on their corresponding (associated) ftimes is
  // NOT a proper GREEDY RULE, since the objective function we're trying to optimize is not
  // the number of COMPLETED COMPATIBLE tasks, rather, the TOTAL VALUE of COMPLETED COMPATIBLE tasks.
  public static ArrayList<Integer> weightedSchedulingProblem ( ArrayList<Integer> accepted_task_ids) {

  return accepted_task_ids;

  }



  /* Intermediate SINGLE Resource Interval Scheduling: */
  public static ArrayList<Integer> intermediateSchedulingProblem (Hashtable<Integer, ArrayList<Integer> > ft, int [] st_array, ArrayList<Integer> st, ArrayList<Integer> accepted_task_ids) {

    // ftimes are the keys in ft Hashtable:
    // ft.keySet() returns a set of key in Hashtable ft
    ArrayList<Integer> ft_sorted_list = new ArrayList<Integer>(ft.keySet()); 
    // First we have to sort the Finishing times:
    Collections.sort(ft_sorted_list); // O(nlogn)

    // Iterated over the sorted ftimes and add 
    // the stime corresponding to the ftime of the first task
    // in list of tasks with a same ftime
    int task_id;
    for(Integer ft_key : ft_sorted_list) {
      // task_id is the id of the first task in the task list
      // because all other tasks with same finishing time are
      // NOT COMPATIBLE (INCOMPATIBLE) with the first task in
      // the list:
      task_id = ( ft.get(ft_key) ).get(0);
      st.add(st_array[task_id]);
    } // O(n)

    // we have to finish the first (the task with a min ftime) first:
    // to do so, we add its corresponding task_id to the list of accepted_task_ids:
    task_id = ( ft.get(ft_sorted_list.get(0)) ).get(0);
    accepted_task_ids.add(task_id);
    int best_ftime = ft_sorted_list.get(0);
    ft_sorted_list.remove(0);
    int i = 1;
    // iterate over the list of sorted UNIQUE ftimes
    for ( Integer ftime : ft_sorted_list ) {
      if ( st.get(i) < best_ftime )  i++;
      else {
        // add the task id to the accepted_task_list:
        task_id = ( ft.get(ftime) ).get(0);
        accepted_task_ids.add(task_id);
        best_ftime = ftime;
	i++;
      }
    } // O(n)
    return accepted_task_ids;
  }  // O(nlogn)

  /* Simple SINGLE Resource Interval Scheduling: */ // O(nlogn)
  public static ArrayList<Integer> simpleSchedulingProblem (Hashtable<Integer, Integer> ft, int [] st_array, ArrayList<Integer> st_list, ArrayList<Integer> accepted_task_ids) {
    // we retrieve finishing times for all tasks:
    // (Note: ft.keySet() returns a Set view of the keys contained in this map.
    ArrayList<Integer> sorted_fts = new ArrayList<Integer>(ft.keySet());
    
    // we sort tasks based on their corresponding finishing time:
    // (Note: sort is a static method of the Collections class)
    Collections.sort(sorted_fts); // O(nlogn)

    // we add (starting time, task index) to the Hashtable st
    // based on the task index of sorted finishing times:
    // in the ft Hashtable:
    for(int i=0; i < sorted_fts.size(); i++){
      st_list.add(st_array[ft.get(sorted_fts.get(i))]); 
    }  // O(n)

    int best_ft = sorted_fts.get(0);
    sorted_fts.remove(0);
    accepted_task_ids.add(ft.get(best_ft));
    int i=1;
    for(Integer ftime : sorted_fts) {
      if (st_list.get(i) < best_ft) i++;
      else {
	accepted_task_ids.add(ft.get(ftime));
	best_ft = ftime;
	i++;
      }
    } // O(n)
    return accepted_task_ids;
  } // O(nlogn)

  // putting records with the same key into the hashtable
  // is different from collision. But we can use the chaining method
  // to overcome this issue similar to collision.
  // NOTE: Collision happens when the hash function, maps two distinct keys into the
  // same hash bucket.
  public static Hashtable<Integer, ArrayList<Integer>> hashtableListPopulate(Hashtable<Integer, ArrayList<Integer>> ht, int [] input_array) {
    // val is the list that keep tracks of 
    // values assigned to each hash bucket with a same key:
    ArrayList<Integer> val;
    // populate the hashtable
    for (int i=0; i<input_array.length; i++) {
      // if the hash key does NOT already exists in our hashtable then:
      if(!ht.containsKey(input_array[i])) {
	// create a new list
	val = new ArrayList<Integer>();
	// add the value to the list
        val.add(i);
	// assign the list of values to the hash key
        ht.put(input_array[i], val);
      }
      // if the hash key already exists
      else {
	// get its corresponding list of values
        val = ht.get(input_array[i]);
	// add the new item to the list
        val.add(i);
      }
    }
    return ht;
  }

  public static Hashtable<Integer, Integer> hashtablePopulate(Hashtable<Integer, Integer> ht, int [] input_array) {
    // assuming ftimes are unique, they can be used as hash keys
    for (int i=0; i<input_array.length; i++)
      ht.put(input_array[i], i);
    return ht;
  }
}
