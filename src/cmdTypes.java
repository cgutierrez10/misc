// Command types
// S = settings codes
// P = point (move to coord at safe z then plunge to max depth, bring back to safe-z)
// V = vertex (z down, cut from current coord to x,y, offset z max of 'last pass' z)
// C = circle (point with a widening circle cut pattern, bring back to safe-z)
// D = drop (z-axis z space coord 1 then z space coord 2)
public enum cmdTypes {
		S,
		P,
		V,
		C,
		D
}
