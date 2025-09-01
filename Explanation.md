# Explanations

## Strategy Pattern

I applied the **Strategy Pattern** to separate reporting logic from the file system structure.

* I created a `Report` interface that defines three common methods:

    * `generate(FileSystemItem item, List<Criterion> criteria)`
    * `reportFile(FileItem file, String indent, List<Criterion> criteria)`
    * `reportDirectory(Directory dir, String indent, List<Criterion> criteria)`

* I then implemented different strategies:

    * **ShowReport** → displays the actual matching content.
    * **CountReport** → counts the number of matching lines.

* This design allows the reporting behavior to be chosen at runtime, without modifying `FileItem` or `Directory`. In other words, the pattern **decouples reporting logic from the composite structure**.

* For example, if a new type of reporting is needed, there is no need to add new methods to `FileItem` or `Directory`. Instead, we can define a new strategy that implements the `Report` interface.

* `FileItem` and `Directory` simply expose their content through methods like `countLineMatching()` and `showLineMatching()`. The report strategies decide how that content is processed and displayed.

* The `generate()` method in each report strategy decides whether to display the actual content or display the count, and then traverses the composite structure accordingly.

---

## Template Method

I also applied the **Template Method Pattern** to define how lines of text are checked against different matching rules.

* I created an abstract class `Criterion` that provides a method `matching(String line)`, which serves as the **template method**.

* The method `matched(String line)` acts as a **hook method**, and is overridden by subclasses to provide concrete matching logic.

* Two concrete implementations were created:

    * **TextCriterion** → checks if a line contains a specific substring.
    * **RegexCriterion** → checks if a line matches a given regular expression.

* The abstract `Criterion` defines the general workflow for matching lines, while subclasses customize only the condition-checking logic.

* This ensures all criteria follow the same evaluation process, keeps the code clean, avoids duplication, and makes the system easy to extend with new types of criteria.

## Appropriate Use of Containers

I mostly used List<> for this project because they naturally represent ordered collections (just as in real-life order).

* `List<String>` in `FileItem` to store lines of text from each file.
* `List<FileSystemItem>` in `Directory` to store files and subdirectories.
* `List<Criterion>` to store a sequence of criteria provided by a user.

I also use `Map<String, Directory>` to keep track of directory by path.