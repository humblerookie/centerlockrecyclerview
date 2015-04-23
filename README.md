# centerlockrecyclerview
Center Lock RecyclerView
This is a recyclerview scroll listener implementation that has a center locking (auto rescroll to center) feature in horizontal mode.

 
 
 Note : 1) This works only for  elements whose size is smaller than width of screen
        2) need to provide a marginLeft of 1dp to the recyclerview item (For detecting nearest element to center right 
        edge ,left edge distance  is used ,having a margin on a single end avoids this)
        3) This can be improved. Would be working on it as and when I find time.
