# Humsafar


Humsafar - Cab Booking System
=============================

Design Explanation
------------------

Introduction
------------
This system models a modern cab booking platform inspired by real-world services like Uber and Ola. The key goal of this design is to provide a modular, extensible, and maintainable architecture that follows core object-oriented principles and well-known design patterns. The system allows riders to request rides, assigns drivers using different strategies, supports real-time notifications, and offers flexible fare calculations through composition and decoration. The design encourages future expansion whi...

Application of SOLID Principles
-------------------------------
Single Responsibility Principle (SRP) is applied throughout the system. The User, Rider, and Driver classes focus solely on user attributes and interactions. Dispatcher manages driver assignment and availability, while NotificationService deals exclusively with observer management and event notification. Fare calculation is encapsulated in the FareCalculator hierarchy, and Receipt handles the generation of payment receipts.

Open/Closed Principle (OCP) is achieved through extensible strategies and decorators. For instance, new DriverMatchingStrategy implementations can be added without altering the Dispatcher class. Similarly, the fare calculation mechanism is open for extension — one can easily add new pricing decorators (such as for holiday pricing or special coupons) without modifying existing fare calculators.

Liskov Substitution Principle (LSP) is ensured through proper interface implementation. All DriverMatchingStrategy implementations, such as NearestDriverMatchingStrategy and BestRatedDriverMatchingStrategy, can be substituted wherever the DriverMatchingStrategy interface is used. Similarly, all classes implementing FareCalculator behave consistently and can replace the base interface.

Interface Segregation Principle (ISP) is applied by defining focused interfaces such as DriverMatchingStrategy and RideObserver. These interfaces expose only the methods that are necessary for their roles, ensuring that implementing classes are not forced to implement unused functionality.

Dependency Inversion Principle (DIP) is respected in components like Dispatcher and NotificationService. The Dispatcher class depends on the abstraction DriverMatchingStrategy rather than any concrete strategy implementation, making it flexible and extensible. Likewise, NotificationService depends on the RideObserver interface, promoting loose coupling.

Design Patterns Used
--------------------
Several classic design patterns were applied to enhance flexibility and maintainability:

- The Singleton Pattern ensures that there is only one instance of Dispatcher and NotificationService, reflecting the centralized nature of driver dispatch and notification management in a ride-sharing platform.

- The Strategy Pattern is used to implement flexible driver-matching logic. The DriverMatchingStrategy interface allows for various matching algorithms, such as finding the nearest driver (NearestDriverMatchingStrategy) or the best-rated driver (BestRatedDriverMatchingStrategy).

- The Decorator Pattern is employed in the fare calculation process. The FareCalculator interface and its decorators, such as SurgePricingDecorator and DiscountDecorator, allow dynamic composition of pricing rules without modifying the core fare calculation logic.

- The Observer Pattern is used to manage real-time notifications. RideObserver implementations (RiderNotificationObserver and DriverNotificationObserver) receive updates from NotificationService whenever the ride status changes, enabling dynamic user feedback and UI updates.

Simplifications, Assumptions, and Trade-offs
--------------------------------------------
To maintain clarity and focus on demonstrating architectural principles, several simplifications were made. The system models location as static coordinates without actual GPS integration, and real-world concurrency and thread-safety concerns are not explicitly addressed. Payment integration is simplified to an abstract PaymentMethod interface without a backend. The state transitions of a ride (RideStatus) are invoked through direct method calls, whereas in a production environment this would typically b...

Assumptions include in-memory management of driver availability and ride history, with no persistent storage modeled. Dispatcher functionality assumes a centralized, single-instance model and does not account for distributed system scenarios. Wallet management is simplified and does not reflect transactional safeguards needed for production.

Trade-offs focused on readability and extensibility over production-readiness. More advanced features, such as traffic-based ETAs, real-time map updates, ride pooling, and multi-region support, are not included but the architecture makes it easy to incorporate such enhancements later.

Conclusion
----------
Overall, this design demonstrates a strong application of object-oriented principles and design patterns to model a realistic cab booking system. The architecture encourages maintainability and extensibility, making it a solid foundation for building more advanced, production-ready features in the future.
