// Lavet af Lea - s245072
package appLogic.report;

import java.util.Set;
import appLogic.Summary;

public record Report(double hoursUsed, Set<Summary> activitySummaries, double expectedRemainingHours) {}