<h1 align = "center"> Merge-sort Benchmark</h1><br>

<h2> &#128269; About the project </h2>

<p>Java application that compares two versions of the merge-sort algorithm, one using concurrent programming and the other sequential. After running each version, the benchmark script will show the most efficient version in each case.</p><br>

<h2> &#128302; Technologies Used </h2><br>

<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,python" />
  </a>
</p>

<h2> &#128296; Dependencies and Execution </h2><br>

<ol>
  <li>To run the program, you must make sure you have <code>Python 3</code> and <code>JDK 21 or higher</code> installed on your machine;</li>
  <li>After making sure of the dependencies, open the <code>Concurrent</code> and <code>Sequential</code> directories and run the code in each one separately;</li>
  <li>After running the directories separately, make sure that the program finished successfully and that the <code>output</code> directory was generated correctly in each version;</li>
  <li>Run the python script by running the code below in your terminal: </li>
</ol>

```markdown
python3 compute_speedup.py
```

<h2> &#128218; Methodology </h2><br>

<p>The following equation was used to calculate the standard deviation $\sigma$ of the execution times of the sequential and concurrent versions:</p>

$$ \sigma = \sqrt{\frac{1}{20} \sum_{i=1}^{20} \(t_i - t_M\)^2} $$

<p>where $t_i$ is the time recorded in the *i*-th execution of the version in question and $t_M$ is the average time of the twenty executions. A low standard deviation indicates that the data points tend to be close to the mean of that data, while a high standard deviation indicates that the data points are spread over a wide range of values.</p>

<p>Specifically regarding the comparative analysis between the sequential and concurrent versions, it is still necessary to determine the performance gain (*speed-up*) eventually obtained with the concurrent version. This calculation was performed using the following equation:</p>

$$ S = \frac{T_s}{T_c} $$

<p>where $S$ is the *speed-up*, $T_s$ is the average time spent by the sequential version and $T_c$ is the average time spent by the concurrent version. If the value of *speed-up* is greater than 1, it follows that the concurrent version is, on average, in fact better in terms of performance than the sequential version.</p>

<br><h3 align = "center"> - By <a href = "https://www.linkedin.com/in/breno-barbosa-de-oliveira-810866275/" target = "_blank">Breno</a> & <a href = "https://www.linkedin.com/in/vanessa-maria-392301212/" target = "_blank">Vanessa</a> - </h3>
