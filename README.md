# Path Finding Visualizer (JavaFX)

This project is a **JavaFX-based Path Finding Visualizer** that demonstrates how different pathfinding algorithms work in real-time on a grid. It allows users to interactively create obstacles, choose algorithms, and visualize how paths are computed step by step.

---

## 🚀 Features

* **Interactive Grid System**

  * Adjustable rows, columns, and cell size
  * Click to create/remove walls (obstacles)

* **Algorithm Visualization**

  * BFS (Breadth-First Search)
  * DFS (Depth-First Search)
  * Dijkstra’s Algorithm

* **Path Visualization**

  * Real-time animation of traversal
  * Shortest path highlighted clearly

* **Custom Controls**

  * Choose algorithm dynamically
  * Control speed of visualization
  * Select movement directions (4-direction / 8-direction)

* **User Controls**

  * Run algorithm
  * Clear path
  * Reset grid
  * Erase wall mode

---

## 🖥️ UI Overview

* **Black cells** → Walls (blocked paths)
* **Blue cells** → Visited nodes
* **Orange cells** → Final shortest path
* **Red cell** → Destination

---

## 🧠 Algorithms Used

### 1. Breadth-First Search (BFS)

* Guarantees shortest path in unweighted graphs
* Explores level by level

### 2. Depth-First Search (DFS)

* Explores deeply before backtracking
* Does NOT guarantee shortest path

### 3. Dijkstra’s Algorithm

* Finds shortest path with weighted logic
* More efficient for complex grids

---

## 🛠️ Technologies Used

* Java 17
* JavaFX (UI Framework)
* Eclipse IDE
* Object-Oriented Programming (OOP)

---

## ⚙️ Project Structure

```
src/
 ├── algorithms/
 │    ├── BFS.java
 │    ├── DFS.java
 │    ├── Dijkstra.java
 │    └── PathAlgorithm.java
 │
 ├── app/
 │    └── PathFindingVisualizer.java
 │
 ├── grid/
 │    ├── Cell.java
 │    └── Grid.java
 │
 ├── enums/
      ├── CellType.java
      └── DirectionMode.java
```

---

## ▶️ Getting Started

Follow these steps to run the project locally:

### 1. Clone the repository

```
git clone https://github.com/VEERA14GPV/Path-Finding-Visualizer.git
cd Path-Finding-Visualizer
```

---

### 2. Setup JavaFX

Download JavaFX SDK from:
👉 https://gluonhq.com/products/javafx/

Extract and note the path.

---

### 3. Configure VM Options

Add this in your run configuration:

```
--module-path "C:\path\to\javafx\lib" --add-modules javafx.controls,javafx.fxml
```

---

### 4. Run the Application

Run:

```
PathFindingVisualizer.java
```

---

## 📊 How It Works

1. Create grid using settings
2. Add walls (obstacles)
3. Select algorithm
4. Click **Run**
5. Watch traversal + shortest path

---

## 🎯 Learning Outcomes

* Understanding graph traversal algorithms
* Visualizing shortest path computation
* JavaFX UI development
* Event-driven programming

---

## 📌 Future Improvements

* Add A* (A-Star) algorithm
* Add weighted grid support
* Save/load grid configurations
* Improve UI animations

---

## 🤝 Contributing

Contributions are welcome. Feel free to:

* Open issues
* Suggest improvements
* Submit pull requests

---

## 📸 Application Preview

<img width="1916" height="1019" alt="1" src="https://github.com/user-attachments/assets/71fdadc7-d50e-4cbf-bc7c-019d127e5951" />

Visualization of BFS algorithm finding the shortest path in a grid with obstacles.

---

## 👨‍💻 Author

**Veera (VEERA14GPV)**
GitHub: https://github.com/VEERA14GPV

---
