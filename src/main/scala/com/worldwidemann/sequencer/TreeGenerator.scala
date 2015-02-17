/*
 * Sequencer – purely algorithmic number sequence identification
 *
 * Copyright (c) 2015 Philipp Emanuel Weidmann <pew@worldwidemann.com>
 *
 * Nemo vir est qui mundum non reddat meliorem.
 *
 * Released under the terms of the GNU General Public License, Version 3
 */

package com.worldwidemann.sequencer

import scala.collection.mutable.ListBuffer

class TreeGenerator(maxChildren: Int) {
  def getTrees(nodes: Int): Seq[Node] = {
    if (nodes <= 0)
      return Seq()
    if (nodes == 1)
      return Seq(new Node)

    getTrees(nodes - 1)
      .map(tree => expandTree(tree, tree))
      .flatten
      // Remove duplicates (see Node's "equals" method)
      .distinct
  }

  // Returns all trees generated by adding a single node to the tree
  private def expandTree(node: Node, root: Node): Seq[Node] = {
    val trees = new ListBuffer[Node]

    if (node.children.size < maxChildren) {
      // Generate new tree by adding child node
      node.children += new Node
      trees += root.getCopy
      // Restore previous state
      node.children.remove(node.children.size - 1)
    }

    // Recursively traverse tree
    trees ++ node.children
      .map(expandTree(_, root))
      .flatten
  }
}