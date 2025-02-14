# S25-lab05

## Answer for Task 3

Issue 1: Duplicate draw function.

Problem: The `draw` function duplicates the logic for handling different file formats (JPEG and PNG). This makes the code harder to maintain and extend when new file formats are introduced.

Refactoring: We can use the Strategy design pattern to encapsulate the file writing logic. Create an interface FileWriterStrategy with a method write(Shape shape, String filename). Implement this interface for each file format (e.g., JPEGWriterStrategy, PNGWriterStrategy). The draw function will then use a FileWriterStrategy to handle the file writing.

```java
public interface FileWriterStrategy {
    void write(Shape shape, String filename) throws IOException;
}

public class JPEGWriterStrategy implements FileWriterStrategy {
    @Override
    public void write(Shape shape, String filename) throws IOException {
        try (Writer writer = new JPEGWriter(filename + ".jpeg")) {
            Line[] lines = shape.toLines();
            shape.draw(writer, lines);
        }
    }
}

public class PNGWriterStrategy implements FileWriterStrategy {
    @Override
    public void write(Shape shape, String filename) throws IOException {
        try (Writer writer = new PNGWriter(filename + ".png")) {
            Line[] lines = shape.toLines();
            shape.draw(writer, lines);
        }
    }
}

public class Drawing {
    private List<Shape> shapes;

    public Drawing(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public void draw(FileWriterStrategy writerStrategy, String filename) {
        for (Shape shape : shapes) {
            try {
                writerStrategy.write(shape, filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

Issue 2: Exposing Internal Details of Shapes

Problem: The `draw` function explicitly creates an array of `Line` objects and passes it to the `Shape`'s `draw` method. This exposes internal details of the `Shape` class and violates the encapsulation principle.

Refactoring: Move the responsibility of converting shapes to lines and drawing them into the `Shape` class itself. The `Shape` class should have a method `draw(Writer writer)` that handles its own drawing logic. Have shapes manage their own line collections internally.

```java
public interface Shape {
    void draw(Writer writer) throws IOException;
}

public class Rectangle implements Shape {
    private final int[][] points;

    public Rectangle(int[] point1, int[] point2, int[] point3, int[] point4) {
        this.points = new int[][]{point1, point2, point3, point4};
    }

    @Override
    public void draw(Writer writer) throws IOException {
        Line[] lines = toLines();
        for (Line line : lines) {
            if (writer instanceof JPEGWriter) {
                writer.write(line.toJPEG());
            } else if (writer instanceof PNGWriter) {
                writer.write(line.toPNG());
            }
        }
    }

    private Line[] toLines() {
        return new Line[]{
            new Line(this.points[0], this.points[1]),
            new Line(this.points[1], this.points[2]),
            new Line(this.points[2], this.points[3]),
            new Line(this.points[3], this.points[0])
        };
    }
}

public class Drawing {
    private List<Shape> shapes;

    public Drawing(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public void draw(FileWriterStrategy writerStrategy, String filename) {
        for (Shape shape : shapes) {
            try (Writer writer = writerStrategy.createWriter(filename)) {
                shape.draw(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```
