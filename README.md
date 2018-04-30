# NegaScout-PVS-with-Principal-Variation-Reordering
## Step One: Building a tree - What and Why.
Three positive integers should be used in building trees: branching factor b, height h, and
approximation Approx.
A tree node should store the following information, and only that:
1) its own static evaluation, an integer simulating what value a static evaluation function
would produce for the corresponding game position without doing any search;
2) its daughters, in some originally-generated order;
3) its daughters, in an order that might get changed by PV reordering during a search.
In building a tree, distinguish between the estimated static evaluation value (E) of a node and the
true search-based value (T). E should be stored with the node, T should be used during tree
construction only and should not be stored. When beginning to build a tree, make a root node
with T randomly chosen between 2500 and -2500 inclusive. For negamax to work, one of its
daughters should have its T equal to the negative of this, and all others should have a T greater
than or equal to that, up to a maximum of +10001. This pattern applies to any interior node and
its daughters: one daughter has the T negated, no daughter has a lesser value.
Leaf nodes should have E=T. Interior nodes should have E=T+∂ , where ∂ is a small number
between -Approx and +Approx chosen randomly for each one individually.
The “best daughter” B of an interior node N is one1 whose T is the same as the negation of that
interior node’s T. The best daughter should be placed randomly among the daughters, it should
not systematically be either the first or the last in sequence, its identity should not be stored in its
parent or elsewhere.
The daughters of an interior node should dominate subtrees whose height is usually one less than
the parent’s subtree’s specified height. Exceptionally, a node with true value 10000 should have
no daughters, representing a won-game position. Interior nodes (but not the root node) should
have branching factor b with 90% chance, b+1 with 5% chance, b-1 with 5% chance.
PV reordering (see Step Three) is to be carried out in an iterative-deepening framework. This
means it will sometimes change the order of daughter nodes after a shallow search and before the
next-deeper search. This in turn means that after a search is completed, the tree is often not as it
was before the search. For this reason, two copies of the set of daughters of a node should be kept.
One copy gives the daughters in the order they were originally generated, the other gives the
same daughters in an order that has perhaps been changed by PV reordering. Before starting any
new search (PVS without reordering, alpha-beta with reordering etc.), the modified-order sets
should be reinitialised to match the originally-generated sets. This will enable you to make
measurements of search performance which compare like with like.

## Step Two: Negamax-style alpha-beta algorithm with iterative deepening
Implement a simple negamax alpha-beta algorithm, with only these enhancements:
(a) code to count the number of static evaluations performed
(b) code to return both a value and a principal variation
(c) code to unpick the returned values appropriately
(d) a parameter indicating whether or not to use the modified daughter
-The function 'Evaluate' is trivial: given a node, it retrieves its preassigned E value.
-The test “no moves available” succeeds when a node has no daughters.
-The operation of “make new node” corresponds to picking the next daughter of an interior node,
starting with the first in the original or the modifiable sequence, as determined by parameter. The
parameter for a call to alpha-beta may be set to true when searching at the root if reordering is
required, and passed on to the first daughter, granddaughter etc..
-The operation of “destroy node” should do nothing2, you will need to keep those nodes for a
second search in order to get comparability of results.
The principal variation from a leaf node is an empty sequence; the principal variation from an
interior node is a sequence whose first element is the best daughter (which caused a cutoff or
provided the return value), and the remainder is the best daughter’s own principal variation.
Arrange for alpha-beta to be called repeatedly with increasing depth limits, alpha= -10000,
beta=+10000.

## Step Three: Principal Variation Reordering
Implement move reordering. After a search, the root node’s modifiable-daughters sequence
should be altered so that the best-seen daughter is in first position, and is followed by other
daughters in the order they first occurred, without repetition. The same is then to be done with its
best-seen daughter, and so on for all elements of the root node’s principal variation.

## Step Four: PVS with iterative deepening
Implement the PVS algorithm, which consists in calling the alpha-beta algorithm with varying
values for its alpha and beta, and occasionally searching a subtree a second time with different
alpha and beta values. See the lecture notes for week 5. Embed the algorithm in a iterativedeepening
framework, as also shown in notes, in which the “resources available” test corresponds
to not having reached the limiting depth of the tree.
