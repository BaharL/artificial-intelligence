# HP 2D Protein Folding

Implementation of the HP 2D Protein Folding exercise using the
SearchStateExplorer framework.

The protein is represented as a sequence of `H` and `P` aminoacids.
The folding is built step by step on a 2D grid.

For each state:

- the next aminoacid can be placed up, down, left or right;
- already occupied cells cannot be used;
- H-H contacts between non-consecutive aminoacids are counted;
- the energy is the opposite of the number of contacts.

The implemented search algorithms are:

- Min-Cost Search
- Best-First Greedy Search
- A*

The step cost is defined as:

`cost = 3 - newContacts`

so configurations producing more H-H contacts have a lower cost.

A simple heuristic is also used for the informed search algorithms.

## Build

From the root of the SearchStateExplorer project:

```bash
./build.sh
```

## Example

From the `examples/ProteinFolding` directory:

```bash
java -jar target/ProteinFolding.jar \
  --size 6 \
  --protein HPHHPH \
  --algos "MINCOST,BFG,A*" \
  -v 0
```

Example result:

```text
contacts = 2
energy = -2
```

The goal is to find a complete folding with as many non-consecutive
H-H contacts as possible, corresponding to minimum energy.
