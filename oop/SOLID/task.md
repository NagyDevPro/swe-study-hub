# Digital Content Subscriptions & Access Control Design Task

## Context
A mid-size media platform sells digital publications (magazines, reports) via subscriptions. Customers can:

- Purchase individual publications (one-off).
- Subscribe to publication bundles (e.g., "Tech Bundle", "Finance Bundle") on monthly/annual terms.
- Upgrade/downgrade between bundles mid-cycle (pro-rated adjustments apply).
- Access content via web/mobile; access checks depend on entitlements and active status.

Organizations can have team plans: an admin purchases seats, assigns/revokes seats to members, and members inherit entitlements from the org's bundle.

Promotions/discounts can apply to purchases or renewals; new promo types are expected to be added frequently.

## Functional Requirements (Core Flows)

### Purchasing
- Buy a one-off publication or start a bundle subscription (monthly/annual).
- Apply zero or more promotions at checkout (e.g., percentage off, fixed credit, "first month free").

### Billing & Adjustments
- Handle proration when upgrading/downgrading bundles mid-cycle.
- Renewals: apply eligible promotions (some promos are renewal-only).

### Entitlements & Access
- Determine if a given user can access a given publication at a point in time (consider one-off purchases, active bundles, org-assigned seats).
- Handle seat assignment/removal by org admins; access reflects changes immediately.

### Content Catalog
- Publications belong to bundles; bundles can change composition over time (new publications added).

### Promotions
- Support multiple promotion types and make it easy to add new types without breaking existing logic.

## Non-Functional Requirements / Constraints
- You'll be asked to show how your design supports change: adding new promotion types, new bundle types (e.g., "metered" plans), or new access rules.
- Favor clear boundaries and extensibility; avoid a "god service."
- Avoid anemic domain models; keep business logic with relevant entities/objects.
- You don't need persistence or web controllers—focus on domain/application logic. Use simple in-memory data as needed.

## What to Deliver
Java code with a small, focused design illustrating the above. Keep it reasonably sized but expressive.

Show how you'd structure:
- Entities/value objects (e.g., Publication, Bundle, Subscription, Promotion, OrgAccount, SeatAssignment, Entitlement).
- Services or use cases (e.g., Checkout, Renewal, AccessCheck, SeatManagement).
- Promotion extensibility (open for new promo types).
- Bundle changes over time and how access is derived.

Include a brief README-style note (can be a .md file) describing how to run a couple of sample flows (no need for actual I/O; a main method or simple test scaffolding is enough).

## SOLID Evaluation Rubric
I'll use later:

- **Single Responsibility**: Clear, cohesive classes; no "kitchen-sink" objects.
- **Open/Closed**: Ease of adding new promotion types/bundle rules without modifying core orchestration.
- **Liskov Substitution**: Polymorphic components behave safely when swapped (e.g., promotions, pricing strategies).
- **Interface Segregation**: Clients depend only on what they need (e.g., access checking vs. billing).
- **Dependency Inversion**: High-level policies depend on abstractions, not concretions (e.g., promotion engines, pricers).

Plus: clarity, naming, and domain expressiveness.